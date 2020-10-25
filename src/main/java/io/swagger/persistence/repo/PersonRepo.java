package io.swagger.persistence.repo;

import io.swagger.persistence.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@RepositoryRestResource(collectionResourceRel = "people", path = "people")
@Repository
public interface PersonRepo extends JpaRepository<Person, Long> {
}
