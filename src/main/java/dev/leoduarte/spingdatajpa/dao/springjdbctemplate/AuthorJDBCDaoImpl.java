package dev.leoduarte.spingdatajpa.dao.springjdbctemplate;

import dev.leoduarte.spingdatajpa.dao.AuthorDao;
import dev.leoduarte.spingdatajpa.domain.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component(value = "authorJDBCDaoImpl")
@RequiredArgsConstructor
public class AuthorJDBCDaoImpl implements AuthorDao {

    private final JdbcTemplate jdbcTemplate;
    private final AuthorJDBCMapper authorRowMapper;

    @Override
    public Author getById(long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM author WHERE id = ?", authorRowMapper, id);
    }

    @Override
    public Author getByFirstNameAndLastName(String firstName, String lastName) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM author WHERE author.first_name = ? AND author.last_name = ?",
                authorRowMapper,
                firstName,
                lastName);
    }

    @Override
    public Author saveNewAuthor(Author author) {
        Long idToBeSaved = getNextId();
        jdbcTemplate.update(
                "INSERT INTO author (id, first_name, last_name) VALUES (?, ?, ?)",
                idToBeSaved,
                author.getFirstName(),
                author.getLastName());

        return getById(idToBeSaved);
    }

    @Override
    public Author updateAuthor(Long id, Author author) {
        jdbcTemplate.update(
                "UPDATE author SET first_name = ?, last_name = ? WHERE author.id = ?",
                author.getFirstName(),
                author.getLastName(),
                id);

        return getById(id);
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM author WHERE id = ?", id);
    }

    private Long getNextId() {
        Long nextId = jdbcTemplate.queryForObject("SELECT next_val FROM author_seq", Long.class);
        jdbcTemplate.update("UPDATE author_seq SET next_val = ?", (nextId + 1));
        return nextId;
    }
}
