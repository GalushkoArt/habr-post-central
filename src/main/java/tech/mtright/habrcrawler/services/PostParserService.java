package tech.mtright.habrcrawler.services;

import tech.mtright.habrcrawler.model.Post;

public interface PostParserService {
    Post parsePost(int id);
}
