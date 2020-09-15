package tech.mtright.habrcrawler.services;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import tech.mtright.habrcrawler.cache.HubCache;
import tech.mtright.habrcrawler.cache.TagCache;

@TestConfiguration
@ComponentScan(basePackages = "tech.mtright.habrcrawler.services")
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
