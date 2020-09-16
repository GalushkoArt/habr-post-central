package tech.mtright.habrcrawler.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import tech.mtright.habrcrawler.model.Hub;
import tech.mtright.habrcrawler.model.Post;
import tech.mtright.habrcrawler.model.Tag;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ServiceMockConfiguration.class)
public class HabrPostParserServiceTest {
    @Autowired
    private HabrPostParserService parserService;

    @Test
    public void parsePostWithCompanyTest() {
        Set<Hub> hubs = Set.of(Hub.builder().name("Блог компании Яндекс").build(),
                Hub.builder().name("Open source").build());
        Set<Tag> tags = Set.of(Tag.builder().name("open source").build(),
                Tag.builder().name("яндекс").build(),
                Tag.builder().name("nginx").build());
        Post post = parserService.parsePost(480090).orElseThrow();
        assertThat(post.getPostId()).isEqualTo(480090);
        assertThat(post.getTitle()).isEqualTo("Open source – наше всё");
        assertThat(post.getDate()).hasYear(2019).hasMonth(12).hasDayOfMonth(13);
        assertThat(post.getAuthor()).isEqualTo("bobuk");
        assertThat(post.getLink()).isEqualTo("https://habr.com/ru/company/yandex/blog/480090/");
        assertThat(post.getCompany()).isEqualTo("Яндекс");
        assertThat(post.getViews()).isGreaterThan(10000);
        assertThat(post.getVotes()).isGreaterThan(0);
        assertThat(post.getHubs()).isEqualTo(hubs);
        assertThat(post.getTags()).isEqualTo(tags);
    }

    @Test
    public void parsePostWithoutCompanyTest() {
        Set<Hub> hubs = Set.of(Hub.builder().name("Научно-популярное").build(),
                Hub.builder().name("Визуализация данных").build(),
                Hub.builder().name("Биотехнологии").build(),
                Hub.builder().name("Открытые данные").build(),
                Hub.builder().name("Здоровье").build());
        Set<Tag> tags = Set.of(Tag.builder().name("covid-19").build(),
                Tag.builder().name("коронавирус").build(),
                Tag.builder().name("coronavirus").build(),
                Tag.builder().name("эпидемия").build(),
                Tag.builder().name("пандемия").build());
        Post post = parserService.parsePost(491974).orElseThrow();
        assertThat(post.getPostId()).isEqualTo(491974);
        assertThat(post.getTitle()).isEqualTo("Коронавирус: почему надо действовать прямо сейчас");
        assertThat(post.getDate()).hasYear(2020).hasMonth(3).hasDayOfMonth(12);
        assertThat(post.getAuthor()).isEqualTo("five");
        assertThat(post.getLink()).isEqualTo("https://habr.com/ru/post/491974/");
        assertThat(post.getCompany()).isNull();
        assertThat(post.getViews()).isGreaterThan(100000);
        assertThat(post.getVotes()).isGreaterThan(0);
        assertThat(post.getHubs()).isEqualTo(hubs);
        assertThat(post.getTags()).isEqualTo(tags);
    }

    @Test
    public void parsePostWithWrongPostId() {
        Optional<Post> post = parserService.parsePost(-99);
        assertThat(post.isEmpty()).isTrue();
    }

}