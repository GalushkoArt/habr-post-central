package tech.mtright.habrcrawler.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.mtright.habrcrawler.dto.HabrUser;
import tech.mtright.habrcrawler.services.SiteSearchService;

import java.util.List;

@RestController
@RequestMapping("/api")
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

    @GetMapping(value = "/findAuthors", produces = "application/json;charset=UTF-8")
    public List<HabrUser> findAuthorsByName(@RequestParam String name) {
        return searchService.searchUsersByName(name);
    }

    @GetMapping(value = "/isTagRelevant")
    @ResponseBody
    public boolean isTagRelevant(@RequestParam String tag) {
        return searchService.isTagRelevant(tag);
    }
}
