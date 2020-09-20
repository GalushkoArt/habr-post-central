package tech.mtright.habrpostcentral.cache.removal_listeners;

import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tech.mtright.habrpostcentral.model.Post;
import tech.mtright.habrpostcentral.repository.PostRepository;
import tech.mtright.habrpostcentral.services.PostSearchService;

import java.util.Optional;

@Component
public class DailyPostRemovalListener implements RemovalListener<Integer, Post> {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostSearchService postSearchService;

    @Override
    @Transactional
    public void onRemoval(RemovalNotification<Integer, Post> removalNotification) {
        Optional<Post> post = postSearchService.searchPostById(removalNotification.getKey());
        post.ifPresent(postRepository::save);
    }
}
