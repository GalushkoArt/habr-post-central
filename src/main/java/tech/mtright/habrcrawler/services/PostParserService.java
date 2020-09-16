package tech.mtright.habrcrawler.services;

import tech.mtright.habrcrawler.model.Post;

import java.util.Optional;

public interface PostParserService {
    Optional<Post> parsePost(int id);
}
