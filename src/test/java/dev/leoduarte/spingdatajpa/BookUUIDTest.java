package dev.leoduarte.spingdatajpa;

import dev.leoduarte.spingdatajpa.domain.original.BookUUID;
import dev.leoduarte.spingdatajpa.domain.original.repository.BookUuidRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("local")
@ComponentScan(basePackages = {"dev.leoduarte.spingdatajpa.bootstrap"})
public class BookUUIDTest {

    @Autowired
    BookUuidRepository bookRepository;

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
        String title = "Book UUID";
        String isbn = "1234567890";
        String publisher = "John Doe";
        Long authorId = 15L;

        BookUUID bookUUID = new BookUUID(null, title, isbn, publisher, authorId);
        UUID uuid = bookRepository.save(bookUUID).getId();

        BookUUID foundEntity = bookRepository.getReferenceById(uuid);

        assertThat(uuid).isEqualTo(foundEntity.getId());
        assertThat(title).isEqualTo(foundEntity.getTitle());
        assertThat(isbn).isEqualTo(foundEntity.getIsbn());
        assertThat(publisher).isEqualTo(foundEntity.getPublisher());
        assertThat(authorId).isEqualTo(foundEntity.getAuthorId());
    }

    @Test
    @Commit
    @Order(3)
    void testCount2MySQL() {
        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(2);
    }
}
