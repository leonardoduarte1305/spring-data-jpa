package dev.leoduarte.spingdatajpa.dao;

import dev.leoduarte.spingdatajpa.domain.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {

    private final DataSource source;

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

    private static Book buildBookToReturn(long id, ResultSet resultSet) throws SQLException {
        return new Book(id, resultSet.getString("title"), resultSet.getString("isbn"), resultSet.getString("publisher"), resultSet.getLong("author_id"));
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
