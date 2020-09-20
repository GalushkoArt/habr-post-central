package tech.mtright.habrpostcentral.services;

import tech.mtright.habrpostcentral.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostSearchService {
    Optional<Post> searchPostById(int id);

    List<Post> searchPostWithIdsBetween(int start, int end);

    List<Post> searchPostsWithIds(List<Integer> ids);
}
