package tech.mtright.habrpostcentral.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tech.mtright.habrpostcentral.model.Tag;
import tech.mtright.habrpostcentral.repository.TagRepository;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TagCache {
    private LoadingCache<String, Tag> tagCache;

    @Autowired
    private CacheLoader<String, Tag> cacheLoader;

    @Autowired
    private TagRepository tagRepository;

    @PostConstruct
    public void init() {
        tagCache = CacheBuilder.newBuilder().build(cacheLoader);
        Map<String, Tag> hubs = tagRepository.findAll().stream().collect(Collectors.toMap(Tag::getName, Function.identity()));
        tagCache.putAll(hubs);
    }

    @Transactional
    public Tag getTag(String name) {
        return tagCache.getUnchecked(name);
    }
}
