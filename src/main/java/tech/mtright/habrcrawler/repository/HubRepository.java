package tech.mtright.habrcrawler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import tech.mtright.habrcrawler.model.Hub;

@RepositoryRestResource
public interface HubRepository extends JpaRepository<Hub, Integer> {
}
