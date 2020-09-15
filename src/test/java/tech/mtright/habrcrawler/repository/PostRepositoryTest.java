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

@RunWith(SpringRunner.class)
@DataJpaTest
class PostRepositoryTest {
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
                .postId(124580)
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
    public void findByTitleIgnoreCaseTest() {
        Optional<Post> post = postRepository.findByTitleIgnoreCase("Title");
        Assert.assertNotNull(post.orElse(null));
    }

    @Test
    @Transactional
    public void findAllByAuthorIgnoreCaseTest() {
        List<Post> postList = postRepository.findAllByAuthorIgnoreCase("Petya");
        Assert.assertEquals(2, postList.size());
    }

    @Test
    @Transactional
    public void findAllByCompanyIgnoreCaseTest() {
        List<Post> postList = postRepository.findAllByCompanyIgnoreCase("Some company");
        Assert.assertEquals(2, postList.size());
    }

    @Test
    @Transactional
    public void findAllByHubsNameIgnoreCaseTest() {
        List<Post> postList = postRepository.findAllByHubsNameIgnoreCase("hub");
        Assert.assertEquals(3, postList.size());
    }

    @Test
    @Transactional
    public void findAllByTagsNameIgnoreCaseTest() {
        List<Post> postList = postRepository.findAllByTagsNameIgnoreCase("Tag");
        Assert.assertEquals(3, postList.size());
    }

    @Test
    @Transactional
    public void findTop10ByCompanyIgnoreCaseOrderByVotesDescTest() {
        List<Post> postList = postRepository.findTop10ByCompanyIgnoreCaseOrderByVotesDesc("Some company");
        Assert.assertTrue(isListDescendingBy(postList, Post::getViews));
        Assert.assertEquals(2, postList.size());
    }

    @Test
    @Transactional
    public void findTop10ByAuthorIgnoreCaseOrderByVotesDescTest() {
        List<Post> postList = postRepository.findTop10ByAuthorIgnoreCaseOrderByVotesDesc("Petya");
        Assert.assertTrue(isListDescendingBy(postList, Post::getViews));
        Assert.assertEquals(2, postList.size());
    }

    @Test
    @Transactional
    public void findTop10ByHubsNameIgnoreCaseOrderByVotesDescTest() {
        List<Post> postList = postRepository.findTop10ByHubsNameIgnoreCaseOrderByVotesDesc("hub");
        Assert.assertTrue(isListDescendingBy(postList, Post::getViews));
        Assert.assertEquals(3, postList.size());
    }

    @Test
    @Transactional
    public void findTop10ByTagsNameIgnoreCaseOrderByVotesDescTest() {
        List<Post> postList = postRepository.findTop10ByTagsNameIgnoreCaseOrderByVotesDesc("tag");
        Assert.assertTrue(isListDescendingBy(postList, Post::getViews));
        Assert.assertEquals(3, postList.size());
    }

    private static <T> boolean isListDescendingBy(List<T> list, Function<T, Integer> valueProvider) {
        int max = Integer.MAX_VALUE;
        for (T t : list) {
            if (valueProvider.apply(t) <= max) {
                max = valueProvider.apply(t);
            } else {
                return false;
            }
        }
        return true;
    }
}