package tech.mtright.habrpostcentral.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.mtright.habrpostcentral.cache.removal_listeners.DailyPostRemovalListener;
import tech.mtright.habrpostcentral.model.Post;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Component
public class DailyPostCache {
    private Cache<Integer, Post> cache;

    @Autowired
    private DailyPostRemovalListener removalListener;

    @PostConstruct
    public void init() {
        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(8, TimeUnit.HOURS)
                .removalListener(removalListener)
                .concurrencyLevel(5)
                .build();
    }

    public void put(Post post) {
        cache.put(post.getPostId(), post);
    }
}
