package dev.leoduarte.spingdatajpa;

import dev.leoduarte.spingdatajpa.domain.problementities.AuthorNPlusOne;
import dev.leoduarte.spingdatajpa.domain.problementities.AuthorNPlusOneRepository;
import dev.leoduarte.spingdatajpa.domain.problementities.BookNPlusOne;
import jakarta.persistence.EntityManager;
import org.hibernate.loader.MultipleBagFetchException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("default")
@ComponentScan(basePackages = {"dev.leoduarte.spingdatajpa.bootstrap"})
public class NPlusOneProblemTest {

    // Execute all tests to see in the logs the differences

    @Autowired
    private AuthorNPlusOneRepository authorNPlusOneRepository;

    @Autowired
    private EntityManager entityManager;

    // With N + 1 Problem occurring // NO STRATEGY
    @Test
    void testNPlusOneProblem() {
        List<AuthorNPlusOne> authors = authorNPlusOneRepository.findAll();
        authors.forEach(authorNPlusOne ->
                System.err.println(authorNPlusOne.getFirstName()
                ));

        for (AuthorNPlusOne author : authors) {
            System.err.println("Author: " + author.getFirstName());
            for (BookNPlusOne book : author.getBooks()) {
                System.err.println("Book: " + book.getTitle());
            }
        }
    }

    // Without the N + 1 Problem occurring // STRATEGY 1 - JOIN FETCH
    @Test
    void testAvoidingNPlusOneProblemStrategy1() {
        List<AuthorNPlusOne> authors = authorNPlusOneRepository.getAllAvoidingNPlusOneProblem();
        authors.forEach(authorNPlusOne ->
                System.err.println(authorNPlusOne.getFirstName()
                ));

        for (AuthorNPlusOne author : authors) {
            System.err.println("Author: " + author.getFirstName());
            for (BookNPlusOne book : author.getBooks()) {
                System.err.println("Book: " + book.getTitle());
            }
        }
    }

    // Without the N + 1 Problem occurring // STRATEGY 2 - LEFT JOIN FETCH
    @Test
    void testAvoidingNPlusOneProblemStrategy2() {
        List<AuthorNPlusOne> authors = authorNPlusOneRepository.getAllAvoidingNPlusOneProblemOtherStrategy();
        authors.forEach(authorNPlusOne ->
                System.err.println(authorNPlusOne.getFirstName()
                ));

        for (AuthorNPlusOne author : authors) {
            System.err.println("Author: " + author.getFirstName());
            for (BookNPlusOne book : author.getBooks()) {
                System.err.println("Book: " + book.getTitle());
            }
        }
    }

    // Without the N + 1 Problem occurring // STRATEGY 3 - GRAPH
    @Test
    void testAvoidingNPlusOneProblemStrategy3() {
        List<AuthorNPlusOne> authors = authorNPlusOneRepository.getAuthorNPlusOneUsingEntityGraph(entityManager);
        authors.forEach(authorNPlusOne ->
                System.err.println(authorNPlusOne.getFirstName()
                ));

        for (AuthorNPlusOne author : authors) {
            System.err.println("Author: " + author.getFirstName());
            for (BookNPlusOne book : author.getBooks()) {
                System.err.println("Book: " + book.getTitle());
            }
        }
    }

    // Without the N + 1 Problem occurring // STRATEGY 4 - BATCH
    @Test
    void testAvoidingNPlusOneProblemStrategy4() {
        List<AuthorNPlusOne> authors = authorNPlusOneRepository.findAll();
        authors.forEach(authorNPlusOne ->
                System.err.println(authorNPlusOne.getFirstName()
                ));

        for (AuthorNPlusOne author : authors) {
            System.err.println("Author: " + author.getFirstName());
            for (BookNPlusOne book : author.getBatchFetchedBooks()) {
                System.err.println("Book: " + book.getTitle());
            }
        }
    }

    // Without the N + 1 Problem occurring // STRATEGY 5 - SUBSELECT
    @Test
    void testAvoidingNPlusOneProblemStrategy5() {
        List<AuthorNPlusOne> authors = authorNPlusOneRepository.findAll();
        authors.forEach(authorNPlusOne ->
                System.err.println(authorNPlusOne.getFirstName()
                ));

        for (AuthorNPlusOne author : authors) {
            System.err.println("Author: " + author.getFirstName());
            for (BookNPlusOne book : author.getSubselectBooks()) {
                System.err.println("Book: " + book.getTitle());
            }
        }
    }

    // Without the N + 1 Problem occurring // STRATEGY 6 - QUERY ASSOCIATION
    @Test
    void testAvoidingNPlusOneProblemStrategy6() {
        List<AuthorNPlusOne> authors = authorNPlusOneRepository.findAll();
        List<BookNPlusOne> books = authorNPlusOneRepository.findBooksWhereAithorIsIn(authors, entityManager);

        authors.forEach(authorNPlusOne ->
                System.err.println(authorNPlusOne.getFirstName()
                ));

        for (AuthorNPlusOne author : authors) {
            System.err.println("Author: " + author.getFirstName());
            for (BookNPlusOne book : author.getSubselectBooks()) {
                System.err.println("Book: " + book.getTitle());
            }
        }
    }

    @Test
    void testWithTwoJoinFetch() {
        InvalidDataAccessApiUsageException exception = assertThrows(InvalidDataAccessApiUsageException.class, () ->
                authorNPlusOneRepository.fetchingPropertiesComJoinFetch()
        );

        assertThat(exception.getMostSpecificCause()).isInstanceOf(MultipleBagFetchException.class);
        assertThat(exception.getMessage()).isEqualTo(
                "org.hibernate.loader.MultipleBagFetchException:" +
                        " cannot simultaneously fetch multiple bags:" +
                        " [dev.leoduarte.spingdatajpa.domain.problementities.AuthorNPlusOne.bookComJoinFetch," +
                        " dev.leoduarte.spingdatajpa.domain.problementities.AuthorNPlusOne.bookComJoinFetch2]");
    }

    @Test
    void testWithTwoJoinFetchInAOneToOneLazyRelationship() {
        authorNPlusOneRepository.fetchingOneToOnePropertiesComJoinFetch();
    }

    @Test
    void testWithoutTwoJoinFetchInAOneToOneLazyRelationship() {
        List<AuthorNPlusOne> encontrados = authorNPlusOneRepository.fetchingOneToOnePropertiesSemJoinFetch();

        System.out.println("encontrados.get(0).getId() = " + encontrados.get(0).getId());
        System.out.println("encontrados.get(0).getBookOneToOneLazyRelation1() = " + encontrados.get(0).getBookOneToOneLazyRelation1());
        System.out.println("encontrados.get(0).getBookOneToOneLazyRelation2() = " + encontrados.get(0).getBookOneToOneLazyRelation2());
    }
}
