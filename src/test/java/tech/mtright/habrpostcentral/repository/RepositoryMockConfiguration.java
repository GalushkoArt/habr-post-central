package tech.mtright.habrpostcentral.repository;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@TestConfiguration
@ComponentScan("tech.mtright.habrpostcentral.repository")
public class RepositoryMockConfiguration {
    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        RestTemplateBuilder mock = Mockito.mock(RestTemplateBuilder.class);
        Mockito.when(mock.build()).thenReturn(new RestTemplate());
        return mock;
    }
}
