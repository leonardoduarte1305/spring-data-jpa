package dev.leoduarte.spingdatajpa;

import dev.leoduarte.spingdatajpa.domain.Book;
import dev.leoduarte.spingdatajpa.repository.BookRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@ActiveProfiles("default")
@ComponentScan(basePackages = {"dev.leoduarte.spingdatajpa.bootstrap"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SpringBootJpaTestSliceTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    // @Commit // The same of @Rollback(value = false)
    @Rollback(value = false) // Spring runs this test inside a transaction and at the end does not rollback the transaction
    @Order(1)
    void testJpaTestSlice() {
        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(9);

        bookRepository.save(new Book(null, "Test Book", "123456789", "Test ISBN"));
        long countAfterInsert = bookRepository.count();

        assertThat(countBefore).isLessThan(countAfterInsert);
    }

    @Test
    @Order(2) // Spring runs this test inside a transaction and at the end its rollback the transaction
    void testJpaTestSliceTransaction() {
        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(10);

        // As the test @Order(1) is annotated with @Rollback(value = false)
        // its possible to assert that the database contains 1 object
    }
}

