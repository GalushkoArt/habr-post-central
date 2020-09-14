package tech.mtright.habrcrawler.services;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.mtright.habrcrawler.cache.HubCache;
import tech.mtright.habrcrawler.cache.TagCache;
import tech.mtright.habrcrawler.model.Hub;
import tech.mtright.habrcrawler.model.Post;
import tech.mtright.habrcrawler.model.Tag;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static javax.xml.bind.DatatypeConverter.parseDateTime;

@Component
public class LinkCrawlerService {
    @Autowired
    HubCache hubCache;
    @Autowired
    TagCache tagCache;

    @SneakyThrows
    public Post parsePost(int id) {
        String url = "https://habr.com/ru/post/" + id;
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla")
                .timeout(30000)
                .referrer("http://google.com")
                .get();
        Element article = doc.selectFirst("article");

        String title = article.selectFirst("h1 span").text();
        String author = article.getElementsByClass("user-info__nickname user-info__nickname_small").text();
        String publishedDate = article.selectFirst("span.post__time").attr("data-time_published").substring(0,16);
        String link = article.selectFirst("#post-content-body").attr("data-io-article-url");
        Elements tagElements = article.getElementsByClass("inline-list inline-list_fav-tags js-post-tags").select("li");
        Elements hubElements = article.getElementsByClass("inline-list inline-list_fav-tags js-post-hubs").select("li");
        Element companyBlock = doc.selectFirst("div.company-info__about");

        String company = null;
        if (companyBlock != null) {
            company = companyBlock.selectFirst("a.page-header__info-title").text();
        }

        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(publishedDate);
        Set<Tag> tags = new HashSet<>();
        Set<Hub> hubs = new HashSet<>();
        for (Element tagElement : tagElements) {
            tags.add(tagCache.getTag(tagElement.selectFirst("a").text()));
        }
        for (Element hubElement : hubElements) {
            hubs.add(hubCache.getHub(hubElement.selectFirst("a").text()));
        }
        return Post.builder()
                .author(author)
                .company(company)
                .link(link)
                .postId(id)
                .title(title)
                .hubs(hubs)
                .tags(tags)
                .date(date)
                .build();
    }
}
