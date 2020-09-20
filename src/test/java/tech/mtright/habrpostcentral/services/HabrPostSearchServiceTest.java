package tech.mtright.habrpostcentral.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tech.mtright.habrpostcentral.model.Post;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class HabrPostSearchServiceTest {
    @Autowired
    private PostSearchService postSearchService;

    @Test
    public void searchRealPostByIdTest() {
        Optional<Post> post = postSearchService.searchPostById(491974);
        assertThat(post).isNotEmpty();
    }

    @Test
    public void searchPostByWrongIdTest() {
        Optional<Post> post = postSearchService.searchPostById(-11);
        assertThat(post).isEmpty();
    }

    @Test
    public void searchPostWithIdsBetweenTest() {
        List<Post> posts = postSearchService.searchPostWithIdsBetween(500000, 500020);
        assertThat(posts).size().isGreaterThan(1);
    }

    @Test
    public void searchPostsWithIdsTest() {
        List<Integer> ids = List.of(500000, 500002, 500004, 500006, 500008, 500010, 500012, 500014, 500016, 500018,
                500020, 500022, 500024, 500026, 500028, 500030, 500032, 500034, 500036, 500038);
        List<Post> posts = postSearchService.searchPostsWithIds(ids);
        assertThat(posts).size().isGreaterThan(1);
    }
}