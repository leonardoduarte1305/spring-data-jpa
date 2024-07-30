package dev.leoduarte.spingdatajpa.domain.problementities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorNPlusOneRepository extends JpaRepository<AuthorNPlusOne, Long> {

    // STRATEGY 2 - JOIN FETCH
    @Query("SELECT a FROM AuthorNPlusOne a JOIN FETCH a.books")
    List<AuthorNPlusOne> getAllAvoidingNPlusOneProblem();

    // STRATEGY 2 - LEFT JOIN FETCH
    @Query("SELECT a FROM AuthorNPlusOne a LEFT JOIN FETCH a.books")
    List<AuthorNPlusOne> getAllAvoidingNPlusOneProblemOtherStrategy();
}
