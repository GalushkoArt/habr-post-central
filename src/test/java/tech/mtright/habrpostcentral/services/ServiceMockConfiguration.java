package tech.mtright.habrpostcentral.services;

import com.google.common.cache.CacheLoader;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;
import tech.mtright.habrpostcentral.cache.HubCache;
import tech.mtright.habrpostcentral.cache.TagCache;
import tech.mtright.habrpostcentral.model.Hub;
import tech.mtright.habrpostcentral.model.Tag;
import tech.mtright.habrpostcentral.repository.HubRepository;
import tech.mtright.habrpostcentral.repository.TagRepository;

import static org.mockito.ArgumentMatchers.anyString;

@TestConfiguration
@ComponentScan(basePackages = "tech.mtright.habrpostcentral.services")
public class ServiceMockConfiguration {
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public HubCache hubCache() {
        HubCache mock = Mockito.mock(HubCache.class);
        Mockito.when(mock.getHub(anyString())).thenReturn(Hub.builder().build());
        return mock;
    }

    @Bean
    public TagCache tagCache() {
        TagCache mock = Mockito.mock(TagCache.class);
        Mockito.when(mock.getTag(anyString())).thenReturn(Tag.builder().build());
        return mock;
    }

    @MockBean
    public CacheLoader<String, Hub> hubCacheLoader;

    @MockBean
    public CacheLoader<String, Tag> tagCacheLoader;

    @MockBean
    public HubRepository hubRepository;

    @MockBean
    public TagRepository tagRepository;
}
