package dev.leoduarte.spingdatajpa.dao;

import dev.leoduarte.spingdatajpa.domain.Book;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class BookDaoImpl implements BookDao {

    private final DataSource source;

    public BookDaoImpl(DataSource source) {
        this.source = source;
    }

    @Override
    public Book getById(long id) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = source.getConnection();
            statement = connection.createStatement();
            // This approach allows SQL Injection, so this is not a good use
            resultSet = statement.executeQuery("SELECT * FROM book WHERE id = " + id);
            if (resultSet.next()) {
                Book book = new Book();
                book.setId(id);
                book.setAuthorId(resultSet.getLong("author_id"));
                book.setIsbn(resultSet.getString("isbn"));
                book.setTitle(resultSet.getString("title"));
                book.setPublisher(resultSet.getString("publisher"));

                System.out.println("Found book = " + book);
                return book;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Entity not found with id = " + id, e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }

                if (statement != null) {
                    statement.close();
                }

                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error closing one or more of those resources.", e);
            }
        }

        return null;
    }
}
