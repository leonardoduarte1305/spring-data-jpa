package dev.leoduarte.spingdatajpa.dao.oldjdbc;

import dev.leoduarte.spingdatajpa.dao.BookDao;
import dev.leoduarte.spingdatajpa.domain.Author;
import dev.leoduarte.spingdatajpa.domain.Book;
import dev.leoduarte.spingdatajpa.repository.AuthorRepository;
import dev.leoduarte.spingdatajpa.repository.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;


@DataJpaTest
@ComponentScan(basePackages = {"dev.leoduarte.spingdatajpa.dao", "dev.leoduarte.spingdatajpa.bootstrap"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@Import(BookDaoImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("local")
class BookDaoImplIntegrationTest {

    @Autowired
    BookDao<Book> bookDao;

    @Autowired // Just to get the id, because it is dinamically generated
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Test
    void getById() {
        Long bookId = bookRepository.findAll().stream().findFirst().get().getId();
        Book book = bookDao.getById(bookId);
        Assertions.assertThat(book).isNotNull();
    }

    @Test
    void getBookByTitleAndPublisher() {
        String title = "Title 1";
        String publisher = "Plublisher 21";
        Book book = bookDao.getByTitleAndPublisher(title, publisher);
        Assertions.assertThat(book).isNotNull();
    }

    @Test
    void saveBook() {
        Book bookToSave = getNewBook();
        Book book = bookDao.saveNewBook(bookToSave);

        Assertions.assertThat(book).isNotNull();
    }

    @Test
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
    void deleteBookById() {
        Book bookToSave = getNewBook();
        Book book = bookDao.saveNewBook(bookToSave);

        Assertions.assertThat(book).isNotNull();

        bookDao.deleteById(book.getId());

        Book deletedBook = bookDao.getById(book.getId());

        Assertions.assertThat(deletedBook).isNull();
    }

    private Book getNewBook() {
        String title = "Title 1";
        String publisher = "Plublisher 21";
        String isbn = "ISBN 1";
        Author author = authorRepository.findAll().stream().findFirst().get();
        return new Book(null, title, isbn, publisher, author);
    }
}
