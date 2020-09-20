package tech.mtright.habrpostcentral.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import tech.mtright.habrpostcentral.model.Tag;

import java.util.Optional;

@RepositoryRestResource
@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    Optional<Tag> getByName(String name);
}
