package tech.mtright.habrcrawler.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.mtright.habrcrawler.services.SiteSearchService;

import java.util.List;

@RestController
public class SearchController {
    @Autowired
    private SiteSearchService searchService;

    @GetMapping(value = "/getCompanies", produces = "application/json;charset=UTF-8")
    public List<String> getCompaniesByName(@RequestParam String name) {
        return searchService.searchCompaniesByName(name);
    }

    @GetMapping(value = "/getHubs", produces = "application/json;charset=UTF-8")
    public List<String> getHubsByName(@RequestParam String name) {
        return searchService.searchHubsByName(name);
    }
}
