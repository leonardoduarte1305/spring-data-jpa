package dev.leoduarte.spingdatajpa;

import dev.leoduarte.spingdatajpa.domain.compositekey.BookCompositeKey;
import dev.leoduarte.spingdatajpa.domain.compositekey.CompositeKey;
import dev.leoduarte.spingdatajpa.repository.BookCompositeKeyRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("local")
@ComponentScan(basePackages = {"dev.leoduarte.spingdatajpa.bootstrap"})
public class BookCompositeKeyTest {

    @Autowired
    BookCompositeKeyRepository bookRepository;

    @Test
    @Commit
    @Order(1)
    void testMySQL() {
        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(1);
    }

    @Test
    @Commit
    @Order(2)
    void testGetByIdMySQL() {
        String title = "Book Composite Key";
        String isbn = "1234567890";
        String publisher = "John Doe";
        Long authorId = 15L;

        CompositeKey compositeKey = new CompositeKey(title, isbn);
        BookCompositeKey bookCompositeKey = new BookCompositeKey(compositeKey.getTitle(), compositeKey.getIsbn(), publisher, authorId);
        bookRepository.save(bookCompositeKey);

        BookCompositeKey foundEntity = bookRepository.findById(compositeKey)
                .orElseThrow(() -> new RuntimeException("Entity not found"));

        assertThat(title).isEqualTo(foundEntity.getTitle());
        assertThat(isbn).isEqualTo(foundEntity.getIsbn());
        assertThat(publisher).isEqualTo(foundEntity.getPublisher());
        assertThat(authorId).isEqualTo(foundEntity.getAuthorId());
    }

    @Test
    @Commit
    @Order(3)
    void testGetByIdUsingOneOfThemNullMySQL() {
        String title = "Book Composite Key 2";
        String isbn = null;
        String publisher = "John Doe";
        Long authorId = 15L;

        BookCompositeKey bookCompositeKey = new BookCompositeKey(title, isbn, publisher, authorId);
        assertThrows(DataIntegrityViolationException.class, () -> bookRepository.save(bookCompositeKey));
    }

    @Test
    @Commit
    @Order(4)
    void testCount2MySQL() {
        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(2);
    }
}
