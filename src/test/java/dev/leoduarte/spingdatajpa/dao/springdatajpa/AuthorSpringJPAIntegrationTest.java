package dev.leoduarte.spingdatajpa.dao.springdatajpa;

import dev.leoduarte.spingdatajpa.dao.AuthorDao;
import dev.leoduarte.spingdatajpa.domain.springdatajpa.AuthorSpringJPA;
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
@Import({AuthorSpringDataJPADaoImpl.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("local")
public class AuthorSpringJPAIntegrationTest {

    @Autowired
    AuthorDao<AuthorSpringJPA> authorDao;

    @Test
    @Commit
    @Order(1)
    void saveAuthor() {
        String firstName = "SpringJPA First Name";
        String lastName = "SpringJPA Last Name";
        AuthorSpringJPA authorToSave = new AuthorSpringJPA(null, firstName, lastName);
        AuthorSpringJPA author = authorDao.saveNewAuthor(authorToSave);

        Assertions.assertThat(author).isNotNull();
    }

    @Test
    @Order(2)
    void getById() {
        String firstName = "First Name";
        String lastName = "Last Name";
        AuthorSpringJPA authorToSave = new AuthorSpringJPA(null, firstName, lastName);

        AuthorSpringJPA author = authorDao.saveNewAuthor(authorToSave);
        Assertions.assertThat(author).isNotNull();

        AuthorSpringJPA foundAuthor = authorDao.getById(author.getId());

        Assertions.assertThat(foundAuthor.getFirstName()).isEqualTo(firstName);
        Assertions.assertThat(foundAuthor.getLastName()).isEqualTo(lastName);
    }

    @Test
    @Order(3)
    void getAuthorByFirstNameAndLastName() {
        String firstName = "Unique First Name";
        String lastName = "Unique Last Name";
        AuthorSpringJPA savedAuthor = authorDao.saveNewAuthor(new AuthorSpringJPA(null, firstName, lastName));

        Assertions.assertThat(savedAuthor.getId()).isNotNull();

        AuthorSpringJPA author = authorDao.getByFirstNameAndLastName(firstName, lastName);

        Assertions.assertThat(author).isNotNull();
    }

    @Test
    @Order(4)
    void updateAuthor() {
        AuthorSpringJPA authorToSave = getNewAuthor();
        AuthorSpringJPA authorAlreadySaved = authorDao.saveNewAuthor(authorToSave);

        Assertions.assertThat(authorAlreadySaved).isNotNull();

        String firstName = "Completely FirstName";
        authorAlreadySaved.setFirstName(firstName);
        AuthorSpringJPA authorUpdated = authorDao.updateAuthor(authorAlreadySaved.getId(), authorAlreadySaved);

        Assertions.assertThat(authorUpdated.getFirstName()).isEqualTo(firstName);
    }

    @Test
    @Order(5)
    void deleteAuthorById() {
        AuthorSpringJPA authorToSave = getNewAuthor();
        AuthorSpringJPA savedAuthor = authorDao.saveNewAuthor(authorToSave);

        Assertions.assertThat(savedAuthor).isNotNull();

        authorDao.deleteById(savedAuthor.getId());

        assertThrows(JpaObjectRetrievalFailureException.class, () -> authorDao.getById(savedAuthor.getId()));
    }

    private static AuthorSpringJPA getNewAuthor() {
        String firstName = "First Name 1";
        String lastName = "Last Name 1";
        return new AuthorSpringJPA(null, firstName, lastName);
    }
}
