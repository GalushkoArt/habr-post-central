package tech.mtright.habrcrawler.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import tech.mtright.habrcrawler.model.Post;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ServiceMockConfiguration.class)
public class HabrPostParserServiceTest {
    @Autowired
    private HabrPostParserService parserService;

    @Test
    public void parsePostWithCompanyTest() {
        Post post = parserService.parsePost(480090);
        assertThat(post.getPostId()).isEqualTo(480090);
        assertThat(post.getTitle()).isEqualTo("Open source – наше всё");
        assertThat(post.getDate()).hasYear(2019).hasMonth(12).hasDayOfMonth(13);
        assertThat(post.getAuthor()).isEqualTo("bobuk");
        assertThat(post.getLink()).isEqualTo("https://habr.com/ru/company/yandex/blog/480090/");
        assertThat(post.getCompany()).isEqualTo("Яндекс");
        assertThat(post.getViews()).isGreaterThan(10000);
        assertThat(post.getVotes()).isGreaterThan(0);
        assertThat(post.getHubs()).size().isGreaterThan(0);
        assertThat(post.getTags()).size().isGreaterThan(0);
    }

    @Test
    public void parsePostWithoutCompanyTest() {
        Post post = parserService.parsePost(491974);
        assertThat(post.getPostId()).isEqualTo(491974);
        assertThat(post.getTitle()).isEqualTo("Коронавирус: почему надо действовать прямо сейчас");
        assertThat(post.getDate()).hasYear(2020).hasMonth(3).hasDayOfMonth(12);
        assertThat(post.getAuthor()).isEqualTo("five");
        assertThat(post.getLink()).isEqualTo("https://habr.com/ru/post/491974/");
        assertThat(post.getCompany()).isNull();
        assertThat(post.getViews()).isGreaterThan(100000);
        assertThat(post.getVotes()).isGreaterThan(0);
        assertThat(post.getHubs()).size().isGreaterThan(0);
        assertThat(post.getTags()).size().isGreaterThan(0);

    }

}