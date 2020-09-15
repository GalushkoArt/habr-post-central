package tech.mtright.habrcrawler.services;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static tech.mtright.habrcrawler.utils.ParserUtils.getDocumentFromLink;

@Log4j2
@Service
public class HabrSearchService implements SiteSearchService {
    private static final String POSTS_LIST = "div.posts_list";
    public static final String HUBS_AND_COMPANIES_SERARCH = "https://habr.com/ru/search/?target_type=hubs&order_by=relevance&q=";
    public static final String HABR_HOME_PAGE = "https://habr.com/ru/all/";
    public static final String RESULT_TITLE = "a.list-snippet__title-link";

    @SneakyThrows
    @Override
    public int findLastPostOnSite() {
        Document doc = getDocumentFromLink(HABR_HOME_PAGE);
        Element postListElement = doc.selectFirst(POSTS_LIST).selectFirst("li");
        if (postListElement.select("article").size() > 0) {
            return Integer.parseInt(postListElement.attr("id").replaceAll("\\D", ""));
        }
        log.error("Can't find last id on habr.com");
        return -1;
    }

    @Override
    public List<String> searchCompaniesByName(String name) {
        return searchTargetByName(name, "company");
    }

    @Override
    public List<String> searchHubsByName(String name) {
        return searchTargetByName(name, "hub");
    }

    @SneakyThrows
    private List<String> searchTargetByName(String name, String target) {
        Document doc = getDocumentFromLink(HUBS_AND_COMPANIES_SERARCH + name);
        List<String> targets = new ArrayList<>();
        Elements results = doc.getElementById("hubs").select("li");
        for (Element result : results) {
            result = result.selectFirst(RESULT_TITLE);
            if (result.attr("href").contains(target)) {
                targets.add(result.text());
            }
        }
        return targets;
    }

}
