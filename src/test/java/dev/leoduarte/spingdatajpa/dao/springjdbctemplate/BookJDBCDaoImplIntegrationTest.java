package dev.leoduarte.spingdatajpa.dao.springjdbctemplate;

import dev.leoduarte.spingdatajpa.dao.BookDao;
import dev.leoduarte.spingdatajpa.domain.Author;
import dev.leoduarte.spingdatajpa.domain.Book;
import dev.leoduarte.spingdatajpa.repository.AuthorRepository;
import dev.leoduarte.spingdatajpa.repository.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;


@DataJpaTest
@ComponentScan(basePackages = {"dev.leoduarte.spingdatajpa.dao.springjdbctemplate"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@Import(BookDaoImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("local")
class BookJDBCDaoImplIntegrationTest {

    @Autowired
    @Qualifier("bookJDBCDaoImpl")
    BookDao bookDao;

    @Autowired // Just to get the id, because it is dinamically generated
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Test
    @Order(1)
    @Commit
    void saveBook() {
        Book bookToSave = getNewBook();
        Book book = bookDao.saveNewBook(bookToSave);

        Assertions.assertThat(book).isNotNull();
    }

    @Test
    @Order(2)
    void getById() {
        Long bookId = bookRepository.findAll().stream().findFirst().get().getId();
        Book book = bookDao.getById(bookId);
        Assertions.assertThat(book).isNotNull();
    }

    @Test
    @Order(3)
    void getBookByTitleAndPublisher() {
        String title = "Title";
        String publisher = "Plublisher";
        Book book = bookDao.getByTitleAndPublisher(title, publisher);
        Assertions.assertThat(book).isNotNull();
    }

    @Test
    @Order(4)
    void updateBook() {
        Book bookToSave = getNewBook();
        Book bookAlreadySaved = bookDao.saveNewBook(bookToSave);

        Assertions.assertThat(bookAlreadySaved).isNotNull();

        String newPublisher = "Completely New Publisher";
        bookAlreadySaved.setPublisher(newPublisher);
        Book bookUpdated = bookDao.updateBook(bookAlreadySaved.getId(), bookAlreadySaved);

        Assertions.assertThat(bookUpdated.getPublisher()).isEqualTo(newPublisher);
    }

    @Test
    @Order(5)
    void deleteBookById() {
        Book bookToSave = getNewBook();
        Book book = bookDao.saveNewBook(bookToSave);

        Assertions.assertThat(book).isNotNull();

        bookDao.deleteById(book.getId());
        assertThrows(EmptyResultDataAccessException.class, () -> bookDao.getById(book.getId()));
    }

    private Book getNewBook() {
        String title = "Title";
        String publisher = "Plublisher";
        String isbn = "ISBN 1";
        Author author = authorRepository.findAll().stream().findFirst().get();
        return new Book(null, title, isbn, publisher, author);
    }
}
