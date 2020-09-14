package tech.mtright.habrcrawler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.mtright.habrcrawler.model.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Post findByPostId(int id);
    Post findByTitle(String title);
    List<Post> findAllByCompany(String company);
    List<Post> findAllByAuthor(String author);
    List<Post> findAllByHubsName(String hubName);
    List<Post> findAllByTagsName(String tagName);
}
