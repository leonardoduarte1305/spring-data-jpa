package dev.leoduarte.spingdatajpa.domain.problementities;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthorNPlusOneCustomRepository {

    private final EntityManager em;

    // STRATEGY 3 - GRAPH
    public List<AuthorNPlusOne> getAuthorNPlusOneUsingEntityGraph() {
        EntityGraph<AuthorNPlusOne> graph = em.createEntityGraph(AuthorNPlusOne.class);
        graph.addAttributeNodes("books");

        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.loadgraph", graph);

        return em.createQuery("SELECT a FROM AuthorNPlusOne a", AuthorNPlusOne.class)
                .setHint("javax.persistence.loadgraph", graph)
                .getResultList();
    }

    public List<BookNPlusOne> findBooksWhereAithorIsIn(List<AuthorNPlusOne> authors) {
        return em.createQuery("SELECT b FROM BookNPlusOne b WHERE b.author IN :authors", BookNPlusOne.class)
                .setParameter("authors", authors)
                .getResultList();
    }
}
