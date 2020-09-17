package tech.mtright.habrpostcentral.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import tech.mtright.habrpostcentral.model.Tag;

@RepositoryRestResource
public interface TagRepository extends JpaRepository<Tag, Integer> {
}
