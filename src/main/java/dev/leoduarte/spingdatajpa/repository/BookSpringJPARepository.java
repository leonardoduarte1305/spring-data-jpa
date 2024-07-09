package dev.leoduarte.spingdatajpa.repository;

import dev.leoduarte.spingdatajpa.domain.springdatajpa.BookSpringJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface BookSpringJPARepository extends JpaRepository<BookSpringJPA, Long> {

    Optional<BookSpringJPA> findBookSpringJPAByTitleAndPublisher(String title, String publisher);

    BookSpringJPA readByTitle(String title);

    @Nullable
    BookSpringJPA getByTitle(@Nullable String title);

    Stream<BookSpringJPA> findAllByTitleNotNull();
}
