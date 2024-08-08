package dev.leoduarte.spingdatajpa.domain.problementities;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface AuthorNPlusOneRepository extends JpaRepository<AuthorNPlusOne, Long> {

    // STRATEGY 2 - JOIN FETCH
    @Query("SELECT a FROM AuthorNPlusOne a JOIN FETCH a.books")
    List<AuthorNPlusOne> getAllAvoidingNPlusOneProblem();

    // STRATEGY 2 - LEFT JOIN FETCH
    @Query("SELECT a FROM AuthorNPlusOne a LEFT JOIN FETCH a.books")
    List<AuthorNPlusOne> getAllAvoidingNPlusOneProblemOtherStrategy();

    // STRATEGY 3 - GRAPH
    default List<AuthorNPlusOne> getAuthorNPlusOneUsingEntityGraph(EntityManager em) {
        EntityGraph<AuthorNPlusOne> graph = em.createEntityGraph(AuthorNPlusOne.class);
        graph.addAttributeNodes("books");

        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.loadgraph", graph);

        return em.createQuery("SELECT a FROM AuthorNPlusOne a", AuthorNPlusOne.class)
                .setHint("javax.persistence.loadgraph", graph)
                .getResultList();
    }

    default List<BookNPlusOne> findBooksWhereAithorIsIn(List<AuthorNPlusOne> authors, EntityManager em) {
        return em.createQuery("SELECT b FROM BookNPlusOne b WHERE b.author IN :authors", BookNPlusOne.class)
                .setParameter("authors", authors)
                .getResultList();
    }

    @Query("SELECT a FROM AuthorNPlusOne a " +
            "JOIN FETCH a.bookComJoinFetch nodeA " +
            "JOIN FETCH a.bookComJoinFetch2 nodeB ")
    List<AuthorNPlusOne> fetchingPropertiesComJoinFetch();

    @Query("SELECT a FROM AuthorNPlusOne a " +
            "JOIN FETCH a.bookOneToOneLazyRelation1 nodeA " +
            "JOIN FETCH a.bookOneToOneLazyRelation2 nodeB ")
    List<AuthorNPlusOne> fetchingOneToOnePropertiesComJoinFetch();

    @Query("SELECT a FROM AuthorNPlusOne a ")
    List<AuthorNPlusOne> fetchingOneToOnePropertiesSemJoinFetch();
}
