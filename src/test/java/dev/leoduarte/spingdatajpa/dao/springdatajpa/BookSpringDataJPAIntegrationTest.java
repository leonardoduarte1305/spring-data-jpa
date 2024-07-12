package dev.leoduarte.spingdatajpa.dao.springdatajpa;

import dev.leoduarte.spingdatajpa.dao.BookDao;
import dev.leoduarte.spingdatajpa.domain.springdatajpa.AuthorSpringJPA;
import dev.leoduarte.spingdatajpa.domain.springdatajpa.BookSpringJPA;
import dev.leoduarte.spingdatajpa.repository.AuthorSpringJPARepository;
import dev.leoduarte.spingdatajpa.repository.BookSpringJPARepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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

        assertThat(book).isNotNull();
    }

    @Test
    @Order(2)
    void getById() {
        Long bookId = bookRepository.findAll().stream().findFirst().get().getId();
        BookSpringJPA book = bookDao.getById(bookId);
        assertThat(book).isNotNull();
    }

    @Test
    @Order(3)
    void getBookByTitleAndPublisher() {
        String title = "Unique Title";
        String publisher = "Unique Plublisher";
        BookSpringJPA savedNewBook = bookDao.saveNewBook(new BookSpringJPA(null, title, "isbn", publisher, 123L));

        assertThat(savedNewBook.getId()).isNotNull();

        BookSpringJPA book = bookDao.getByTitleAndPublisher(title, publisher);
        assertThat(book).isNotNull();
    }

    @Test
    @Order(4)
    void updateBook() {
        BookSpringJPA bookToSave = getNewBook();
        BookSpringJPA bookAlreadySaved = bookDao.saveNewBook(bookToSave);

        assertThat(bookAlreadySaved).isNotNull();

        String newPublisher = "Completely New Publisher";
        bookAlreadySaved.setPublisher(newPublisher);
        BookSpringJPA bookUpdated = bookDao.updateBook(bookAlreadySaved.getId(), bookAlreadySaved);

        assertThat(bookUpdated.getPublisher()).isEqualTo(newPublisher);
    }

    @Test
    @Order(5)
    void deleteBookById() {
        BookSpringJPA bookToSave = getNewBook();
        BookSpringJPA book = bookDao.saveNewBook(bookToSave);

        assertThat(book).isNotNull();

        bookDao.deleteById(book.getId());
        assertThrows(JpaObjectRetrievalFailureException.class, () -> bookDao.getById(book.getId()));

    }

    @Test
    void testEmptyResultexception() {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            bookRepository.readByTitle("bla bla bla");
        });
    }

    @Test
    void testNullParam() {
        assertNull(bookRepository.getByTitle(null));
    }

    @Test
    void testNoExcepton() {
        assertNull(bookRepository.getByTitle("foo"));
    }

    @Test
    void testStream() {
        AtomicInteger count = new AtomicInteger();

        bookRepository.findAllByTitleNotNull().forEach(bookSpringJPA -> {
            count.incrementAndGet();
        });

        assertThat(count.get()).isGreaterThan(5);
    }

    @Test
    void testBookFuture() throws ExecutionException, InterruptedException {
        BookSpringJPA bookToSave = new BookSpringJPA(null, "Abracadabra", "isbn único", "A Fancy publisher", 10L);
        BookSpringJPA savedBook = bookDao.saveNewBook(bookToSave);

        Future<BookSpringJPA> futureBook = bookRepository.queryByTitle(savedBook.getTitle());

        BookSpringJPA book = futureBook.get();
        assertNotNull(book);
    }

    @Test
    void testBookBiTitleWithQuery() {
        BookSpringJPA bookToSave = new BookSpringJPA(null, "Abracadabra", "isbn único", "A Fancy publisher", 10L);
        BookSpringJPA savedBook = bookDao.saveNewBook(bookToSave);

        BookSpringJPA foundBook = bookRepository.findByTitleWithQuery(savedBook.getTitle());
        assertNotNull(foundBook.getId());
    }

    @Test
    void testBookBiTitleWithQueryNamed() {
        BookSpringJPA bookToSave = new BookSpringJPA(null, "Abracadabra 2", "isbn único", "A Fancy publisher", 10L);
        BookSpringJPA savedBook = bookDao.saveNewBook(bookToSave);

        BookSpringJPA foundBook = bookRepository.findByTitleWithQueryNamed(savedBook.getTitle());
        assertNotNull(foundBook.getId());
    }

    @Test
    void testBookBiTitleWithNativeQuery() {
        BookSpringJPA bookToSave = new BookSpringJPA(null, "Abracadabra 3", "isbn único", "A Fancy publisher", 10L);
        BookSpringJPA savedBook = bookDao.saveNewBook(bookToSave);

        BookSpringJPA foundBook = bookRepository.findByTitleWithNativeQuery(savedBook.getTitle());
        assertNotNull(foundBook.getId());
    }

    @Test
    void testBookBiTitleWithNamedQuery() {
        BookSpringJPA bookToSave = new BookSpringJPA(null, "Abracadabra 4", "isbn único", "A Fancy publisher", 10L);
        BookSpringJPA savedBook = bookDao.saveNewBook(bookToSave);

        BookSpringJPA foundBook = bookRepository.namedQueryToUse(savedBook.getTitle());
        assertNotNull(foundBook.getId());
    }

    private BookSpringJPA getNewBook() {
        String title = "Title";
        String publisher = "Plublisher";
        String isbn = "ISBN 1";
        AuthorSpringJPA author = authorRepository.findAll().stream().findFirst().get();
        return new BookSpringJPA(null, title, isbn, publisher, author.getId());
    }
}
