package dev.leoduarte.spingdatajpa.dao.springdatajpa;

import dev.leoduarte.spingdatajpa.dao.BookDao;
import dev.leoduarte.spingdatajpa.domain.springdatajpa.AuthorSpringJPA;
import dev.leoduarte.spingdatajpa.domain.springdatajpa.BookSpringJPA;
import dev.leoduarte.spingdatajpa.repository.AuthorSpringJPARepository;
import dev.leoduarte.spingdatajpa.repository.BookSpringJPARepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import(BookSpringDataJPADaoImpl.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("local")
public class BookSpringDataJPAIntegrationTest {

    @Autowired
    BookDao<BookSpringJPA> bookDao;

    @Autowired
    BookSpringJPARepository bookRepository;

    @Autowired
    AuthorSpringJPARepository authorRepository;

    @Test
    @Order(1)
    @Commit
    void saveBook() {
        BookSpringJPA bookToSave = getNewBook();
        BookSpringJPA book = bookDao.saveNewBook(bookToSave);

        Assertions.assertThat(book).isNotNull();
    }

    @Test
    @Order(2)
    void getById() {
        Long bookId = bookRepository.findAll().stream().findFirst().get().getId();
        BookSpringJPA book = bookDao.getById(bookId);
        Assertions.assertThat(book).isNotNull();
    }

    @Test
    @Order(3)
    void getBookByTitleAndPublisher() {
        String title = "Unique Title";
        String publisher = "Unique Plublisher";
        BookSpringJPA savedNewBook = bookDao.saveNewBook(new BookSpringJPA(null, title, "isbn", publisher, 123L));

        Assertions.assertThat(savedNewBook.getId()).isNotNull();

        BookSpringJPA book = bookDao.getByTitleAndPublisher(title, publisher);
        Assertions.assertThat(book).isNotNull();
    }

    @Test
    @Order(4)
    void updateBook() {
        BookSpringJPA bookToSave = getNewBook();
        BookSpringJPA bookAlreadySaved = bookDao.saveNewBook(bookToSave);

        Assertions.assertThat(bookAlreadySaved).isNotNull();

        String newPublisher = "Completely New Publisher";
        bookAlreadySaved.setPublisher(newPublisher);
        BookSpringJPA bookUpdated = bookDao.updateBook(bookAlreadySaved.getId(), bookAlreadySaved);

        Assertions.assertThat(bookUpdated.getPublisher()).isEqualTo(newPublisher);
    }

    @Test
    @Order(5)
    void deleteBookById() {
        BookSpringJPA bookToSave = getNewBook();
        BookSpringJPA book = bookDao.saveNewBook(bookToSave);

        Assertions.assertThat(book).isNotNull();

        bookDao.deleteById(book.getId());
        assertThrows(JpaObjectRetrievalFailureException.class, () -> bookDao.getById(book.getId()));

    }

    private BookSpringJPA getNewBook() {
        String title = "Title";
        String publisher = "Plublisher";
        String isbn = "ISBN 1";
        AuthorSpringJPA author = authorRepository.findAll().stream().findFirst().get();
        return new BookSpringJPA(null, title, isbn, publisher, author.getId());
    }
}
