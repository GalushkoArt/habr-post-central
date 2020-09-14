package tech.mtright.habrcrawler.repository;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import java.util.Set;

@RunWith(SpringRunner.class)
@DataJpaTest
class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    @Transactional
    @Rollback(false)
    public void setUp() {
        Post post1 = Post.builder().date(new Date())
                .tags(Set.of(Tag.builder().name("Tag").build()))
                .hubs(Set.of(Hub.builder().name("hub").build()))
                .title("Title")
                .postId(124567)
                .link("https://somelink.com")
                .company(null)
                .author("Petya")
                .build();
        Post post2 = Post.builder().date(new Date())
                .tags(Set.of(Tag.builder().name("Tag").build()))
                .hubs(Set.of(Hub.builder().name("hub").build()))
                .title("Title1")
                .postId(124568)
                .link("https://somelink.com")
                .company("Some company")
                .author("Petya")
                .build();
        postRepository.save(post1);
        postRepository.save(post2);
    }

    @Test
    @Transactional
    public void findByPostId() {
        Post post = postRepository.findByPostId(124567);
        Assert.assertNotNull(post);
    }

    @Test
    @Transactional
    public void findByTitle() {
        Post post = postRepository.findByTitle("Title");
        Assert.assertNotNull(post);
    }

    @Test
    @Transactional
    public void findAllByAuthor() {
        List<Post> postList = postRepository.findAllByAuthor("Petya");
        Assert.assertEquals(2, postList.size());
    }

    @Test
    @Transactional
    public void findAllByCompany() {
        List<Post> postList = postRepository.findAllByCompany("Some company");
        Assert.assertEquals(1, postList.size());
    }

    @Test
    @Transactional
    public void findAllByHubsName() {
        List<Post> postList = postRepository.findAllByHubsName("hub");
        Assert.assertEquals(2, postList.size());
    }

    @Test
    @Transactional
    public void findAllByTagsName() {
        List<Post> postList = postRepository.findAllByTagsName("Tag");
        Assert.assertEquals(2, postList.size());
    }
}