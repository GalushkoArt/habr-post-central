package tech.mtright.habrpostcentral.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tech.mtright.habrpostcentral.model.Hub;
import tech.mtright.habrpostcentral.repository.HubRepository;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class HubCache {
    private LoadingCache<String, Hub> hubCache;

    @Autowired
    private CacheLoader<String, Hub> cacheLoader;

    @Autowired
    private HubRepository hubRepository;

    @PostConstruct
    public void init() {
        hubCache = CacheBuilder.newBuilder().build(cacheLoader);
        Map<String, Hub> hubs = hubRepository.findAll().stream().collect(Collectors.toMap(Hub::getName, Function.identity()));
        hubCache.putAll(hubs);
    }

    @Transactional
    public Hub getHub(String name) {
        return hubCache.getUnchecked(name);
    }
}
