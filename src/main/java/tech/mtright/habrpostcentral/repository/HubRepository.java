package tech.mtright.habrpostcentral.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import tech.mtright.habrpostcentral.model.Hub;

import java.util.Optional;

@RepositoryRestResource
@Repository
public interface HubRepository extends JpaRepository<Hub, Integer> {
    Optional<Hub> getByName(String name);
}
