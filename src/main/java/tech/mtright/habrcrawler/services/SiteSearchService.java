package tech.mtright.habrcrawler.services;

import java.util.List;

public interface SiteSearchService {
    int findLastPostOnSite();

    List<String> searchCompaniesByName(String name);

    List<String> searchHubsByName(String name);
}
