package tech.mtright.habrpostcentral.controllers;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;
import tech.mtright.habrpostcentral.services.PostSearchService;

@TestConfiguration
@ComponentScan("tech.mtright.habrpostcentral.controllers")
public class ControllerMockConfiguration {
    @MockBean
    public PostSearchService postSearchService;

    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        RestTemplateBuilder mock = Mockito.mock(RestTemplateBuilder.class);
        Mockito.when(mock.build()).thenReturn(new RestTemplate());
        return mock;
    }
}
