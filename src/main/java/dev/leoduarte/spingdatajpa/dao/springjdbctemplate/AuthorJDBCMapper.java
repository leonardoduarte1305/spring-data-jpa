package dev.leoduarte.spingdatajpa.dao.springjdbctemplate;

import dev.leoduarte.spingdatajpa.domain.Author;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AuthorJDBCMapper implements RowMapper<Author> {

    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Author(
                rs.getLong("id"),
                rs.getString("first_name"),
                rs.getString("last_name")
        );
    }
}
