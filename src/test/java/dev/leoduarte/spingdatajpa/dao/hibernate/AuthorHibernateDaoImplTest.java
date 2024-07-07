package dev.leoduarte.spingdatajpa.dao.hibernate;

import dev.leoduarte.spingdatajpa.domain.hibernate.AuthorHibernate;
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
@Import({AuthorHibernateDaoImpl.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("local")
class AuthorHibernateDaoImplTest {

    @Autowired
    AuthorHibernateDao authorDao;

    @Test
    @Commit
    @Order(1)
    void saveAuthor() {
        String firstName = "Hibernate First Name";
        String lastName = "Hibernate Last Name";
        AuthorHibernate authorToSave = new AuthorHibernate(null, firstName, lastName);
        AuthorHibernate author = authorDao.saveNewAuthorHibernate(authorToSave);

        Assertions.assertThat(author).isNotNull();
    }

    @Test
    @Order(2)
    void getById() {
        String firstName = "First Name";
        String lastName = "Last Name";
        AuthorHibernate authorToSave = new AuthorHibernate(null, firstName, lastName);

        AuthorHibernate author = authorDao.saveNewAuthorHibernate(authorToSave);
        Assertions.assertThat(author).isNotNull();

        AuthorHibernate foundAuthor = authorDao.getById(author.getId());

        Assertions.assertThat(foundAuthor.getFirstName()).isEqualTo(firstName);
        Assertions.assertThat(foundAuthor.getLastName()).isEqualTo(lastName);
    }

    @Test
    @Order(3)
    void getAuthorByFirstNameAndLastName() {
        String firstName = "Hibernate First Name";
        String lastName = "Hibernate Last Name";
        AuthorHibernate book = authorDao.getByFirstNameAndLastName(firstName, lastName);

        Assertions.assertThat(book).isNotNull();
    }

    @Test
    @Order(4)
    void updateAuthor() {
        AuthorHibernate authorToSave = getNewAuthor();
        AuthorHibernate authorAlreadySaved = authorDao.saveNewAuthorHibernate(authorToSave);

        Assertions.assertThat(authorAlreadySaved).isNotNull();

        String firstName = "Completely FirstName";
        authorAlreadySaved.setFirstName(firstName);
        AuthorHibernate authorUpdated = authorDao.updateAuthorHibernate(authorAlreadySaved.getId(), authorAlreadySaved);

        Assertions.assertThat(authorUpdated.getFirstName()).isEqualTo(firstName);
    }

    @Test
    @Order(5)
    void deleteAuthorById() {
        AuthorHibernate authorToSave = getNewAuthor();
        AuthorHibernate savedAuthor = authorDao.saveNewAuthorHibernate(authorToSave);

        Assertions.assertThat(savedAuthor).isNotNull();

        authorDao.deleteById(savedAuthor.getId());
        AuthorHibernate removedEntity = authorDao.getById(savedAuthor.getId());

        Assertions.assertThat(removedEntity).isNull();
    }

    private static AuthorHibernate getNewAuthor() {
        String firstName = "First Name 1";
        String lastName = "Last Name 1";
        return new AuthorHibernate(null, firstName, lastName);
    }

}
