package tech.mtright.habrcrawler.services;

import tech.mtright.habrcrawler.dto.HabrUser;

import java.util.List;

public interface SiteSearchService {
    int findLastPostOnSite();

    boolean isTagRelevant(String tag);

    List<String> searchCompaniesByName(String name);

    List<String> searchHubsByName(String name);

    List<HabrUser> searchUsersByName(String name);
}
