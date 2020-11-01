package io.swagger.persistence.repo;

import io.swagger.annotations.Api;
import io.swagger.persistence.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@Api(tags = "Person Entity")
@RepositoryRestResource(collectionResourceRel = "people", path = "people")
@PreAuthorize("hasRole('ROLE_SERVICE')")
public interface PersonRepo extends JpaRepository<Person, Long> {

    List<Person> findAllByFullNameContainingIgnoreCase(@Param("fullName") String fullName);
}