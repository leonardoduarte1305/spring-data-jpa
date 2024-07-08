package dev.leoduarte.spingdatajpa.dao.springjdbctemplate;

import dev.leoduarte.spingdatajpa.dao.AuthorDao;
import dev.leoduarte.spingdatajpa.domain.Author;
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
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("local")
class AuthorJDBCDaoImplIntegrationTest {

    @Autowired
    @Qualifier("authorJDBCDaoImpl")
    AuthorDao<Author> authorDao;

    @Test
    @Commit
    @Order(1)
    void saveAuthor() {
        String firstName = "JDBC First Name";
        String lastName = "JDBC Last Name";
        Author authorToSave = new Author(null, firstName, lastName);
        Author author = authorDao.saveNewAuthor(authorToSave);

        Assertions.assertThat(author).isNotNull();
    }

    @Test
    @Order(2)
    void getById() {
        String firstName = "First Name";
        String lastName = "Last Name";
        Author authorToSave = new Author(null, firstName, lastName);

        Author author = authorDao.saveNewAuthor(authorToSave);
        Assertions.assertThat(author).isNotNull();

        Author foundAuthor = authorDao.getById(author.getId());

        Assertions.assertThat(foundAuthor.getFirstName()).isEqualTo(firstName);
        Assertions.assertThat(foundAuthor.getLastName()).isEqualTo(lastName);
    }

    @Test
    @Order(3)
    void getAuthorByFirstNameAndLastName() {
        String firstName = "JDBC First Name";
        String lastName = "JDBC Last Name";
        Author book = authorDao.getByFirstNameAndLastName(firstName, lastName);

        Assertions.assertThat(book).isNotNull();
    }

    @Test
    @Order(4)
    void updateAuthor() {
        Author authorToSave = getNewAuthor();
        Author authorAlreadySaved = authorDao.saveNewAuthor(authorToSave);

        Assertions.assertThat(authorAlreadySaved).isNotNull();

        String firstName = "Completely FirstName";
        authorAlreadySaved.setFirstName(firstName);
        Author authorUpdated = authorDao.updateAuthor(authorAlreadySaved.getId(), authorAlreadySaved);

        Assertions.assertThat(authorUpdated.getFirstName()).isEqualTo(firstName);
    }

    @Test
    @Order(5)
    void deleteAuthorById() {
        Author authorToSave = getNewAuthor();
        Author savedAuthor = authorDao.saveNewAuthor(authorToSave);

        Assertions.assertThat(savedAuthor).isNotNull();

        authorDao.deleteById(savedAuthor.getId());
        assertThrows(EmptyResultDataAccessException.class, () -> authorDao.getById(savedAuthor.getId()));
    }

    private static Author getNewAuthor() {
        String firstName = "First Name 1";
        String lastName = "Last Name 1";
        return new Author(null, firstName, lastName);
    }
}
