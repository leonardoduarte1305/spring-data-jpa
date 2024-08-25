package dev.leoduarte.spingdatajpa.dao.hibernate;

import dev.leoduarte.spingdatajpa.domain.hibernate.AuthorHibernate;
import dev.leoduarte.spingdatajpa.domain.hibernate.BookHibernate;
import dev.leoduarte.spingdatajpa.domain.hibernate.repository.AuthorHibernateRepository;
import dev.leoduarte.spingdatajpa.domain.hibernate.repository.BookHibernateRepository;
import jakarta.persistence.EntityManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ComponentScan(basePackages = {"dev.leoduarte.springdatajpa.bootstrap"})
@Import(BookHibernateDaoImpl.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("local")
class BookHibernateDaoImplTest {

    @Autowired
    BookHibernateDao bookDao;

    @Autowired // Just to get the id, because it is dinamically generated
    BookHibernateRepository bookRepository;

    @Autowired
    AuthorHibernateRepository authorRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @Order(1)
    @Commit
    void saveBook() {
        BookHibernate bookToSave = getNewBook();
        BookHibernate book = bookDao.saveNewBook(bookToSave);

        assertThat(book).isNotNull();
    }

    @Test
    @Order(2)
    void getById() {
        Long bookId = bookRepository.findAll().stream().findFirst().get().getId();
        BookHibernate book = bookDao.getById(bookId);
        assertThat(book).isNotNull();
    }

    @Test
    @Order(3)
    void getBookByTitleAndPublisher() {
        String title = RandomStringUtils.random(20);
        String publisher = "Plublisher";
        bookDao.saveNewBook(new BookHibernate(title, "",publisher,null));

        BookHibernate book = bookDao.getByTitleAndPublisher(title, publisher);
        assertThat(book).isNotNull();
    }

    @Test
    @Order(4)
    void updateBook() {
        BookHibernate bookToSave = getNewBook();
        BookHibernate bookAlreadySaved = bookDao.saveNewBook(bookToSave);

        assertThat(bookAlreadySaved).isNotNull();

        String newPublisher = "Completely New Publisher";
        bookAlreadySaved.setPublisher(newPublisher);
        BookHibernate bookUpdated = bookDao.updateBook(bookAlreadySaved.getId(), bookAlreadySaved);

        assertThat(bookUpdated.getPublisher()).isEqualTo(newPublisher);
    }

    @Test
    @Order(5)
    void deleteBookById() {
        BookHibernate bookToSave = getNewBook();
        BookHibernate book = bookDao.saveNewBook(bookToSave);

        assertThat(book).isNotNull();

        bookDao.deleteById(book.getId());
        BookHibernate removedEntity = bookDao.getById(book.getId());

        assertThat(removedEntity).isNull();
    }

    @Test
    @Order(6)
    void getBookByISBN() {
        String isbn = "ISBN 15";
        BookHibernate book = bookDao.findByISBN(isbn);

        assertThat(book).isNotNull();
        assertThat(isbn).isEqualTo(book.getIsbn());
    }

    @Test
    @Order(7)
    void listAllAuthors() {
        List<BookHibernate> found = bookDao.findAll();

        assertThat(found).isNotNull();
        assertThat(found).hasSizeGreaterThan(1);
    }

    @Test
    @Order(8)
    void getByIsbnWithNamedQuery() {
        String isbn = "ISBN 15";
        BookHibernate book = bookDao.getByIsbnWithNamedQuery(isbn);

        assertThat(book).isNotNull();
        assertThat(isbn).isEqualTo(book.getIsbn());
    }

    @Test
    @Order(9)
    void getBookByIsbnWithCriteria() {
        String isbn = "ISBN 15";
        BookHibernate book = bookDao.getBookByIsbnWithCriteria(isbn);

        assertThat(book).isNotNull();
        assertThat(isbn).isEqualTo(book.getIsbn());
    }

    @Test
    @Order(10)
    void getBookByIsbnWithNativeQuery() {
        String isbn = "ISBN 15";
        BookHibernate book = bookDao.getBookByIsbnWithNativeQuery(isbn);

        assertThat(book).isNotNull();
        assertThat(isbn).isEqualTo(book.getIsbn());
    }

    @Test
    @Order(11)
    void listAllBooksPaginated() {
        List<BookHibernate> found = bookDao.findAllBooks(PageRequest.of(0, 5));

        assertThat(found).isNotNull();
        assertThat(found.size()).isEqualTo(5);
    }

    @Test
    void deleteBookByIdWithoutFlushModeCommit() {
        BookHibernate book = getSavedBookHibernate();
        bookDao.deleteByIdWithFlushModeCommit(book.getId());
    }

    @Test
    void deleteBookByIdWithFlushAndAuto() {
        BookHibernate book = getSavedBookHibernate();
        bookDao.deleteByIdWithFlushModeAuto(book.getId());
    }

    private BookHibernate getSavedBookHibernate() {
        BookHibernate bookToSave = getNewBook();
        BookHibernate book = bookDao.saveNewBook(bookToSave);
        assertThat(book).isNotNull();
        return book;
    }

    private BookHibernate getNewBook() {
        String title = "Title";
        String publisher = "Plublisher";
        String isbn = "ISBN 1";
        AuthorHibernate author = authorRepository.findAll().stream().findFirst().get();
        return new BookHibernate(null, title, isbn, publisher, author);
    }
}
