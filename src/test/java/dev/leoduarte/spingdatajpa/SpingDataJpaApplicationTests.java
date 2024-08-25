package dev.leoduarte.spingdatajpa;

import dev.leoduarte.spingdatajpa.domain.original.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("local")
@ComponentScan(basePackages = {"dev.leoduarte.spingdatajpa.bootstrap"})
@SpringBootTest
class SpingDataJpaApplicationTests {

    @Autowired
    BookRepository bookRepository;

    @Test
    void testBookRepository() {
        long count = bookRepository.count();
        assertThat(count).isGreaterThan(0);
    }

    @Test
    void contextLoads() {
    }

}
