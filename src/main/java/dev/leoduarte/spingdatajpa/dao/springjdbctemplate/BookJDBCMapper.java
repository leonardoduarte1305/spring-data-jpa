package dev.leoduarte.spingdatajpa.dao.springjdbctemplate;

import dev.leoduarte.spingdatajpa.dao.AuthorDao;
import dev.leoduarte.spingdatajpa.domain.Author;
import dev.leoduarte.spingdatajpa.domain.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class BookJDBCMapper implements RowMapper<Book> {

    @Qualifier("authorJDBCDaoImpl")
    private final AuthorDao<Author> authorDao;

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Book(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("isbn"),
                rs.getString("publisher"),
                authorDao.getById(rs.getLong("author_id"))
        );
    }
}
