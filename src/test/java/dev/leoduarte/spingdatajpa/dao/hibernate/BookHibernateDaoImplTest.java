package dev.leoduarte.spingdatajpa.dao.hibernate;

import dev.leoduarte.spingdatajpa.domain.hibernate.AuthorHibernate;
import dev.leoduarte.spingdatajpa.domain.hibernate.BookHibernate;
import dev.leoduarte.spingdatajpa.repository.AuthorHibernateRepository;
import dev.leoduarte.spingdatajpa.repository.BookHibernateRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;

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

    @Test
    @Order(1)
    @Commit
    void saveBook() {
        BookHibernate bookToSave = getNewBook();
        BookHibernate book = bookDao.saveNewBook(bookToSave);

        Assertions.assertThat(book).isNotNull();
    }

    @Test
    @Order(2)
    void getById() {
        Long bookId = bookRepository.findAll().stream().findFirst().get().getId();
        BookHibernate book = bookDao.getById(bookId);
        Assertions.assertThat(book).isNotNull();
    }

    @Test
    @Order(3)
    void getBookByTitleAndPublisher() {
        String title = "Title";
        String publisher = "Plublisher";
        BookHibernate book = bookDao.getByTitleAndPublisher(title, publisher);
        Assertions.assertThat(book).isNotNull();
    }

    @Test
    @Order(4)
    void updateBook() {
        BookHibernate bookToSave = getNewBook();
        BookHibernate bookAlreadySaved = bookDao.saveNewBook(bookToSave);

        Assertions.assertThat(bookAlreadySaved).isNotNull();

        String newPublisher = "Completely New Publisher";
        bookAlreadySaved.setPublisher(newPublisher);
        BookHibernate bookUpdated = bookDao.updateBook(bookAlreadySaved.getId(), bookAlreadySaved);

        Assertions.assertThat(bookUpdated.getPublisher()).isEqualTo(newPublisher);
    }

    @Test
    @Order(5)
    void deleteBookById() {
        BookHibernate bookToSave = getNewBook();
        BookHibernate book = bookDao.saveNewBook(bookToSave);

        Assertions.assertThat(book).isNotNull();

        bookDao.deleteById(book.getId());
        BookHibernate removedEntity = bookDao.getById(book.getId());

        Assertions.assertThat(removedEntity).isNull();
    }

    private BookHibernate getNewBook() {
        String title = "Title";
        String publisher = "Plublisher";
        String isbn = "ISBN 1";
        AuthorHibernate author = authorRepository.findAll().stream().findFirst().get();
        return new BookHibernate(null, title, isbn, publisher, author);
    }
}
