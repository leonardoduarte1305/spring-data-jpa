package dev.leoduarte.spingdatajpa.repository;

import dev.leoduarte.spingdatajpa.domain.Author;
import dev.leoduarte.spingdatajpa.domain.springdatajpa.BookSpringJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookSpringJPARepository extends JpaRepository<BookSpringJPA, Long> {

    Optional<BookSpringJPA> findBookSpringJPAByTitleAndPublisher(String title, String publisher);
}
