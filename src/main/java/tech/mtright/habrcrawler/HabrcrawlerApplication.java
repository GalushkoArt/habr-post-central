package tech.mtright.habrcrawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import tech.mtright.habrcrawler.repository.PostRepository;
import tech.mtright.habrcrawler.services.LinkCrawlerService;

@SpringBootApplication
public class HabrcrawlerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(HabrcrawlerApplication.class, args);
    }

}
