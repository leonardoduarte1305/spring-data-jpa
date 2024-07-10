package dev.leoduarte.spingdatajpa.repository;

import dev.leoduarte.spingdatajpa.domain.springdatajpa.BookSpringJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Stream;

@Repository
public interface BookSpringJPARepository extends JpaRepository<BookSpringJPA, Long> {

    @Query(value = "SELECT * FROM book_spring_jpa  WHERE title = :nativeTitle", nativeQuery = true)
    BookSpringJPA findByTitleWithNativeQuery(@Param("nativeTitle") String title);

    @Query("SELECT b FROM BookSpringJPA b WHERE b.title = :completetTitle")
    BookSpringJPA findByTitleWithQueryNamed(@Param("completetTitle") String title);

    @Query("SELECT b FROM BookSpringJPA b WHERE b.title = ?1")
    BookSpringJPA findByTitleWithQuery(String title);

    Optional<BookSpringJPA> findBookSpringJPAByTitleAndPublisher(String title, String publisher);

    BookSpringJPA readByTitle(String title);

    @Nullable
    BookSpringJPA getByTitle(@Nullable String title);

    Stream<BookSpringJPA> findAllByTitleNotNull();

    @Async
    Future<BookSpringJPA> queryByTitle(String title);
}
