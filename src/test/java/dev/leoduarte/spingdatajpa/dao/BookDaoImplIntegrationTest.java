package dev.leoduarte.spingdatajpa.dao;

import dev.leoduarte.spingdatajpa.domain.Book;
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
    BookDao bookDao;

    @Autowired // Just to get the id, because it is dinamically generated
    BookRepository bookRepository;

    @Test
    void getById() {
        Long bookId = bookRepository.findAll().stream().findFirst().get().getId();
        Book book = bookDao.getById(bookId);
        Assertions.assertThat(book).isNotNull();


        // Printing to make sure that it's working fine
        printingBook(book);
    }

    @Test
    void getBookByTitleAndPublisher() {
        String title = "Title 1";
        String publisher = "Plublisher 21";
        Book book = bookDao.getByTitleAndPublisher(title, publisher);
        Assertions.assertThat(book).isNotNull();


        printingBook(book);
    }

    @Test
    void saveBook() {
        String title = "Title 1";
        String publisher = "Plublisher 21";
        String isbn = "ISBN 1";
        Long authorId = 999L;
        Book bookToSave = new Book(null, title, isbn, publisher, authorId);
        Book book = bookDao.saveNewBook(bookToSave);

        Assertions.assertThat(book).isNotNull();


        printingBook(book);
    }

    private static void printingBook(Book book) {
        // Printing to make sure that it's working fine
        System.err.println("Book Id: " + book.getId());
        System.err.println("Book Title: " + book.getTitle());
        System.err.println("Book Isbn: " + book.getIsbn());
        System.err.println("Book Publisher: " + book.getPublisher());
        System.err.println("Book Author Id: " + book.getAuthorId());
    }
}
