package dev.leoduarte.spingdatajpa.domain.original.repository;

import dev.leoduarte.spingdatajpa.domain.original.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
