package tech.mtright.habrcrawler.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import tech.mtright.habrcrawler.model.Hub;
import tech.mtright.habrcrawler.model.Post;
import tech.mtright.habrcrawler.model.Tag;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @Before
    @Transactional
    @Rollback(false)
    public void setUp() {
        Post post1 = Post.builder().date(new Date())
                .tags(Set.of(Tag.builder().name("Tag").build()))
                .hubs(Set.of(Hub.builder().name("hub").build()))
                .title("Title")
                .postId(124567)
                .views(11111)
                .votes(11)
                .link("https://somelink.com")
                .company(null)
                .author("Petya")
                .build();
        Post post2 = Post.builder().date(new Date())
                .tags(Set.of(Tag.builder().name("Tag").build()))
                .hubs(Set.of(Hub.builder().name("hub").build()))
                .title("Title1")
                .postId(124568)
                .views(22222)
                .votes(22)
                .link("https://somelink1.com")
                .company("Some company")
                .author("Petya")
                .build();
        Post post3 = Post.builder().date(new Date())
                .tags(Set.of(Tag.builder().name("Tag").build()))
                .hubs(Set.of(Hub.builder().name("hub").build()))
                .title("Title2")
                .postId(124580)
                .views(33333)
                .votes(33)
                .link("https://somelink2.com")
                .company("Some company")
                .author("Vasya")
                .build();
        Post post4 = Post.builder().date(new Date())
                .tags(Set.of(Tag.builder().name("Tag1").build()))
                .hubs(Set.of(Hub.builder().name("hub1").build()))
                .title("Title3")
                .postId(124585)
                .views(44444)
                .votes(44)
                .link("https://somelink3.com")
                .company("Other company")
                .author("Kolya")
                .build();
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        postRepository.save(post4);
    }

    @Test
    @Transactional
    public void findByPostIdTest() {
        Optional<Post> post = postRepository.findByPostId(124567);
        Assert.assertNotNull(post.orElse(null));
    }

    @Test
    @Transactional
    public void findByPostIdInTest() {
        List<Post> posts = postRepository.findByPostIdIn(List.of(124567, 124568, 124580));
        assertThat(posts).size().isEqualTo(3);
    }

    @Test
    @Transactional
    public void findByTitleIgnoreCaseTest() {
        Optional<Post> post = postRepository.findByTitleIgnoreCase("Title");
        assertThat(post).isNotEmpty();
    }

    @Test
    @Transactional
    public void findAllByAuthorIgnoreCaseTest() {
        List<Post> postList = postRepository.findAllByAuthorIgnoreCase("Petya");
        assertThat(postList).size().isEqualTo(2);
    }

    @Test
    @Transactional
    public void findAllByCompanyIgnoreCaseTest() {
        List<Post> postList = postRepository.findAllByCompanyIgnoreCase("Some company");
        assertThat(postList).size().isEqualTo(2);
    }

    @Test
    @Transactional
    public void findAllByHubsNameIgnoreCaseTest() {
        List<Post> postList = postRepository.findAllByHubsNameIgnoreCase("hub");
        assertThat(postList).size().isEqualTo(3);
    }

    @Test
    @Transactional
    public void findAllByTagsNameIgnoreCaseTest() {
        List<Post> postList = postRepository.findAllByTagsNameIgnoreCase("Tag");
        assertThat(postList).size().isEqualTo(3);
    }

    @Test
    @Transactional
    public void findTop10ByCompanyIgnoreCaseOrderByVotesDescTest() {
        List<Post> postList = postRepository.findTop10ByCompanyIgnoreCaseOrderByVotesDesc("Some company");
        assertThat(isListDescendingBy(postList, Post::getViews));
        assertThat(postList).size().isEqualTo(2);
    }

    @Test
    @Transactional
    public void findTop10ByAuthorIgnoreCaseOrderByVotesDescTest() {
        List<Post> postList = postRepository.findTop10ByAuthorIgnoreCaseOrderByVotesDesc("Petya");
        assertThat(isListDescendingBy(postList, Post::getViews));
        assertThat(postList).size().isEqualTo(2);
    }

    @Test
    @Transactional
    public void findTop10ByHubsNameIgnoreCaseOrderByVotesDescTest() {
        List<Post> postList = postRepository.findTop10ByHubsNameIgnoreCaseOrderByVotesDesc("hub");
        assertThat(isListDescendingBy(postList, Post::getViews));
        assertThat(postList).size().isEqualTo(3);
    }

    @Test
    @Transactional
    public void findTop10ByTagsNameIgnoreCaseOrderByVotesDescTest() {
        List<Post> postList = postRepository.findTop10ByTagsNameIgnoreCaseOrderByVotesDesc("tag");
        assertThat(isListDescendingBy(postList, Post::getViews));
        assertThat(postList).size().isEqualTo(3);
    }

    public static <T, R extends Comparable<R>> boolean isListDescendingBy(List<T> list, Function<T, R> valueProvider) {
        if (list.size() > 0) {
            R max = valueProvider.apply(list.get(0));
            for (T t : list) {
                if (valueProvider.apply(t).compareTo(max) <= 0) {
                    max = valueProvider.apply(t);
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}