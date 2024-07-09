package dev.leoduarte.spingdatajpa.repository;

import dev.leoduarte.spingdatajpa.domain.springdatajpa.AuthorSpringJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorSpringJPARepository extends JpaRepository<AuthorSpringJPA, Long> {

    Optional<AuthorSpringJPA> findByFirstNameAndLastName(String firstName, String lastName);
}
