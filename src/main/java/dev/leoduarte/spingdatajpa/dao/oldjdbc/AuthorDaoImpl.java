package dev.leoduarte.spingdatajpa.dao.oldjdbc;

import dev.leoduarte.spingdatajpa.dao.AuthorDao;
import dev.leoduarte.spingdatajpa.domain.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component("authorDaoImpl")
@Primary
@RequiredArgsConstructor
public class AuthorDaoImpl implements AuthorDao<Author> {

    private final DataSource source;

    @Override
    public Author getById(long id) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement ps = null;

        try {
            connection = source.getConnection();
            ps = connection.prepareStatement("SELECT * FROM author WHERE author.id = ?");
            ps.setLong(1, id);
            resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return buildAuthorToReturn(id, resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Entity not found with id = " + id, e);
        } finally {
            closeAllResources(resultSet, ps, connection);
        }

        return null;
    }

    @Override
    public Author getByFirstNameAndLastName(String firstName, String lastName) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement ps = null;

        try {
            connection = source.getConnection();
            ps = connection.prepareStatement("SELECT * FROM author WHERE first_name = ? AND last_name = ?");
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return buildAuthorToReturn(resultSet.getLong("id"), resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Entity not found with firstName = " + firstName + " and lastName = " + lastName, e);
        } finally {
            closeAllResources(resultSet, ps, connection);
        }

        return null;
    }

    @Override
    public Author saveNewAuthor(Author Author) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement ps = null;

        try {
            connection = source.getConnection();
            long idToBeSaved = getTheNextAuthorId(connection);

            ps = connection.prepareStatement("INSERT INTO author (id, first_name, last_name) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, idToBeSaved);
            ps.setString(2, Author.getFirstName());
            ps.setString(3, Author.getLastName());
            ps.execute();

            return getById(idToBeSaved);
        } catch (SQLException e) {
            throw new RuntimeException("The entity could not be saved properly.", e);
        } finally {
            closeAllResources(resultSet, ps, connection);
        }
    }

    @Override
    public Author updateAuthor(Long id, Author Author) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement ps = null;

        try {
            connection = source.getConnection();

            ps = connection.prepareStatement("UPDATE author SET author.first_name = ?, author.last_name = ? WHERE author.id = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, Author.getFirstName());
            ps.setString(2, Author.getLastName());
            ps.setLong(3, id);
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

            ps = connection.prepareStatement("DELETE FROM author WHERE author.id = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, id);
            ps.execute();

        } catch (SQLException e) {
            throw new RuntimeException("The entity could not be deleted properly.", e);
        } finally {
            closeAllResources(null, ps, connection);
        }
    }

    private long getTheNextAuthorId(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT next_val FROM author_seq");
        if (resultSet.next()) {
            long nextId = resultSet.getLong(1);
            statement.executeUpdate("UPDATE author_seq SET next_val = " + (nextId + 1));

            statement.close();
            resultSet.close();
            return nextId;
        } else {
            statement.close();
            resultSet.close();
            throw new SQLException("Failed to retrieve next sequence value.");
        }
    }

    private static Author buildAuthorToReturn(long id, ResultSet resultSet) throws SQLException {
        return new Author(id, resultSet.getString("first_Name"), resultSet.getString("last_Name"));
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
