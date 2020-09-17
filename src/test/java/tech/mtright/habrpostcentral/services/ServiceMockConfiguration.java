package tech.mtright.habrpostcentral.services;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import tech.mtright.habrpostcentral.cache.HubCache;
import tech.mtright.habrpostcentral.cache.TagCache;

@TestConfiguration
@ComponentScan(basePackages = "tech.mtright.habrpostcentral.services")
public class ServiceMockConfiguration {
    @Bean
    public HubCache hubCache() {
        return new HubCache();
    }

    @Bean
    public TagCache tagCache() {
        return new TagCache();
    }
}
