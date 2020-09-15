package tech.mtright.habrcrawler.services;

import lombok.SneakyThrows;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.mtright.habrcrawler.cache.HubCache;
import tech.mtright.habrcrawler.cache.TagCache;
import tech.mtright.habrcrawler.model.Hub;
import tech.mtright.habrcrawler.model.Post;
import tech.mtright.habrcrawler.model.Tag;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static tech.mtright.habrcrawler.utils.ParserUtils.getDocumentFromLink;

@Service
public class HabrPostParserService implements PostParserService {
    private static final String TITLE = "h1 span";
    private static final String AUTHOR_NAME = "user-info__nickname user-info__nickname_small";
    private static final String POST_BODY = "#post-content-body";
    private static final String ARTICLE_URL = "data-io-article-url";
    private static final String POST_TIME = "span.post__time";
    private static final String TIME_PUBLISHED = "data-time_published";
    private static final String COMPANY_ABOUT_BLOCK = "div.company-info__about";
    private static final String COMPANY_TITLE = "a.page-header__info-title";
    private static final String HUBS_LIST = "inline-list inline-list_fav-tags js-post-hubs";
    private static final String TAGS_LIST = "inline-list inline-list_fav-tags js-post-tags";
    private static final String VOTES_COUNTER = "voting-wjt__counter  voting-wjt__counter_positive  js-score";
    private static final String POST_VIEWS = "post-stats__views-count";

    @Autowired
    HubCache hubCache;
    @Autowired
    TagCache tagCache;

    @SneakyThrows
    public Post parsePost(int id) {
        String url = "https://habr.com/ru/post/" + id;
        Document doc = getDocumentFromLink(url);
        Element article = doc.selectFirst("article");

        String title = article.selectFirst(TITLE).text();
        String author = article.getElementsByClass(AUTHOR_NAME).text();
        String link = article.selectFirst(POST_BODY).attr(ARTICLE_URL);
        String publishedDate = article.selectFirst(POST_TIME).attr(TIME_PUBLISHED).substring(0, 16);
        int votes = Integer.parseInt(doc.getElementsByClass(VOTES_COUNTER).text());
        int views = getViews(doc);
        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(publishedDate);
        String company = getCompanyTitle(doc);

        Set<Tag> tags = getTags(article);
        Set<Hub> hubs = getHubs(article);

        return Post.builder()
                .author(author)
                .company(company)
                .link(link)
                .postId(id)
                .title(title)
                .views(views)
                .votes(votes)
                .hubs(hubs)
                .tags(tags)
                .date(date)
                .build();
    }

    private int getViews(Document document) {
        String views = document.getElementsByClass(POST_VIEWS).text();
        if (views.contains("k")) {
            return (int) (Double.parseDouble(views.replace("k", "").replace(",", ".")) * 1000);
        } else {
            return Integer.parseInt(views);
        }
    }

    private Set<Hub> getHubs(Element article) {
        Set<Hub> hubs = new HashSet<>();
        Elements hubElements = article.getElementsByClass(HUBS_LIST).select("li");
        for (Element hubElement : hubElements) {
            hubs.add(hubCache.getHub(hubElement.selectFirst("a").text()));
        }
        return hubs;
    }

    private Set<Tag> getTags(Element article) {
        Set<Tag> tags = new HashSet<>();
        Elements tagElements = article.getElementsByClass(TAGS_LIST).select("li");
        for (Element tagElement : tagElements) {
            tags.add(tagCache.getTag(tagElement.selectFirst("a").text()));
        }
        return tags;
    }

    private String getCompanyTitle(Document document) {
        Element companyBlock = document.selectFirst(COMPANY_ABOUT_BLOCK);
        if (companyBlock != null) {
            return companyBlock.selectFirst(COMPANY_TITLE).text();
        } else {
            return null;
        }
    }
}
