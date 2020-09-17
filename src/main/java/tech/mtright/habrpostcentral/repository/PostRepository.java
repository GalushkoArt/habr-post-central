package tech.mtright.habrpostcentral.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import tech.mtright.habrpostcentral.model.Post;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface PostRepository extends JpaRepository<Post, Integer> {
    @RestResource(path = "byId")
    Optional<Post> findByPostId(@Param("id") int id);

    @RestResource(path = "byIds")
    List<Post> findByPostIdIn(@Param("ids") List<Integer> ids);

    @RestResource(path = "byTitle")
    Optional<Post> findByTitleIgnoreCase(@Param("title") String title);

    @RestResource(path = "byCompany")
    List<Post> findAllByCompanyIgnoreCase(@Param("company") String company);

    @RestResource(path = "byAuthor")
    List<Post> findAllByAuthorIgnoreCase(@Param("author") String author);

    @RestResource(path = "byHub")
    List<Post> findAllByHubsNameIgnoreCase(@Param("hub") String hubName);

    @RestResource(path = "byTag")
    List<Post> findAllByTagsNameIgnoreCase(@Param("tag") String tagName);

    @RestResource(path = "topOfCompany")
    List<Post> findTop10ByCompanyIgnoreCaseOrderByVotesDesc(@Param("company") String company);

    @RestResource(path = "topOfAuthor")
    List<Post> findTop10ByAuthorIgnoreCaseOrderByVotesDesc(@Param("author") String author);

    @RestResource(path = "topOfHub")
    List<Post> findTop10ByHubsNameIgnoreCaseOrderByVotesDesc(@Param("hub") String hubName);

    @RestResource(path = "topOfTag")
    List<Post> findTop10ByTagsNameIgnoreCaseOrderByVotesDesc(@Param("tag") String tagName);
}
