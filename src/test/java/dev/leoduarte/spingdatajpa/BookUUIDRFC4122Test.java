package dev.leoduarte.spingdatajpa;

import dev.leoduarte.spingdatajpa.domain.BookUUIDRFC4122;
import dev.leoduarte.spingdatajpa.repository.BookUuidRFC4122Repository;
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
public class BookUUIDRFC4122Test {

    @Autowired
    BookUuidRFC4122Repository bookRepository;

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
        String title = "Book UUID RFC 4122";
        String isbn = "1234567890";
        String publisher = "John Doe";
        Long authorId = 15L;

        BookUUIDRFC4122 bookUuidRFC4122 = new BookUUIDRFC4122(null, title, isbn, publisher, authorId);
        UUID uuid = bookRepository.save(bookUuidRFC4122).getId();

        BookUUIDRFC4122 foundEntity = bookRepository.getReferenceById(uuid);

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
