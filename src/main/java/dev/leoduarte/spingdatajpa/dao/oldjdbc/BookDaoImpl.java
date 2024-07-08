package dev.leoduarte.spingdatajpa.dao.oldjdbc;

import dev.leoduarte.spingdatajpa.dao.AuthorDao;
import dev.leoduarte.spingdatajpa.dao.BookDao;
import dev.leoduarte.spingdatajpa.domain.Author;
import dev.leoduarte.spingdatajpa.domain.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.sql.Types.BIGINT;

@Component("bookDaoImpl")
@Primary
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao<Book> {

    private final DataSource source;

    private final AuthorDao<Author> authorDao;

    @Override
    public Book getById(long id) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement ps = null;

        try {
            connection = source.getConnection();
            ps = connection.prepareStatement("SELECT * FROM book WHERE id = ?");
            ps.setLong(1, id);
            resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return buildBookToReturn(id, resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Entity not found with id = " + id, e);
        } finally {
            closeAllResources(resultSet, ps, connection);
        }

        return null;
    }

    @Override
    public Book getByTitleAndPublisher(String title, String publisher) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement ps = null;

        try {
            connection = source.getConnection();
            ps = connection.prepareStatement("SELECT * FROM book WHERE title = ? AND publisher = ?");
            ps.setString(1, title);
            ps.setString(2, publisher);
            resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return buildBookToReturn(resultSet.getLong("id"), resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Entity not found with title = " + title + " and publisher = " + publisher, e);
        } finally {
            closeAllResources(resultSet, ps, connection);
        }

        return null;
    }

    @Override
    public Book saveNewBook(Book book) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement ps = null;

        try {
            connection = source.getConnection();
            long idToBeSaved = getTheNextBookId(connection);

            ps = connection.prepareStatement("INSERT INTO book (id, title, isbn, publisher, author_id) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, idToBeSaved);
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getIsbn());
            ps.setString(4, book.getPublisher());

            if (book.getAuthor() != null) {
                ps.setLong(5, book.getAuthor().getId());
            } else {
                // To make JDBC happy we must set that positional attribute with null
                ps.setNull(5, BIGINT);
            }

            ps.execute();

            return getById(idToBeSaved);
        } catch (SQLException e) {
            throw new RuntimeException("The entity could not be saved properly.", e);
        } finally {
            closeAllResources(resultSet, ps, connection);
        }
    }

    @Override
    public Book updateBook(Long id, Book book) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement ps = null;

        try {
            connection = source.getConnection();

            ps = connection.prepareStatement("UPDATE book SET book.title = ?, book.isbn = ?, book.publisher = ?, book.author_id = ? WHERE book.id = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getIsbn());
            ps.setString(3, book.getPublisher());

            if (book.getAuthor() != null) {
                ps.setLong(4, book.getAuthor().getId());
            } else {
                // To make JDBC happy we must set that positional attribute with null
                ps.setNull(5, BIGINT);
            }

            ps.setLong(5, id);
            ps.execute();

        } catch (SQLException e) {
            throw new RuntimeException("The entity could not be saved properly.", e);
        } finally {
            closeAllResources(resultSet, ps, connection);
        }

        return getById(id);
    }

    @Override
    public void deleteById(Long id) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = source.getConnection();

            ps = connection.prepareStatement("DELETE FROM book WHERE book.id = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, id);
            ps.execute();

        } catch (SQLException e) {
            throw new RuntimeException("The entity could not be deleted properly.", e);
        } finally {
            closeAllResources(null, ps, connection);
        }
    }

    private long getTheNextBookId(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT next_val FROM book_seq");
        if (resultSet.next()) {
            long nextId = resultSet.getLong(1);
            statement.executeUpdate("UPDATE book_seq SET next_val = " + (nextId + 1));

            statement.close();
            resultSet.close();
            return nextId;
        } else {
            statement.close();
            resultSet.close();
            throw new SQLException("Failed to retrieve next sequence value.");
        }
    }

    private Long getTheLastSavedId(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        // SELECT LAST_INSERT_ID() -> is a MySQL only functionality
        ResultSet resultSet = statement.executeQuery("SELECT LAST_INSERT_ID()");
        if (resultSet.next()) {
            long result = resultSet.getLong(1);
            statement.close();
            resultSet.close();

            return result;
        } else {
            statement.close();
            resultSet.close();
            throw new SQLException("No ID was returned.  Please try again.");
        }
    }

    private Book buildBookToReturn(long id, ResultSet resultSet) throws SQLException {
        return new Book(
                id,
                resultSet.getString("title"),
                resultSet.getString("isbn"),
                resultSet.getString("publisher"),
                authorDao.getById(resultSet.getLong("author_id"))
        );
    }

    private static void closeAllResources(ResultSet resultSet, PreparedStatement ps, Connection connection) {
        try {
            closeResultSet(resultSet);
            closePreparedStatement(ps);
            closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException("Error closing one or more of those resources.", e);
        }
    }

    private static void closeResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
    }

    private static void closePreparedStatement(PreparedStatement ps) throws SQLException {
        if (ps != null) {
            ps.close();
        }
    }

    private static void closeConnection(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
