package tech.mtright.habrcrawler.services;

import tech.mtright.habrcrawler.model.Post;

public interface LinkCrawlerService {
    Post parsePost(int id);
}
