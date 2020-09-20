package tech.mtright.habrpostcentral.cache.cache_loaders;

import com.google.common.cache.CacheLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tech.mtright.habrpostcentral.model.Hub;
import tech.mtright.habrpostcentral.repository.HubRepository;

import java.util.Optional;

@Component
public class HubCacheLoader extends CacheLoader<String, Hub> {
    @Autowired
    private HubRepository hubRepository;

    @Override
    @Transactional
    public Hub load(String s) throws Exception {
        Optional<Hub> hub = hubRepository.getByName(s);
        if (hub.isPresent()) {
            return hub.get();
        } else {
            Hub newHub = Hub.builder().name(s).build();
            hubRepository.save(newHub);
            return newHub;
        }
    }
}
