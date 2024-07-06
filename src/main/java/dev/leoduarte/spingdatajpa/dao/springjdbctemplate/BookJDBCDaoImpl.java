package dev.leoduarte.spingdatajpa.dao.springjdbctemplate;

import dev.leoduarte.spingdatajpa.dao.BookDao;
import dev.leoduarte.spingdatajpa.domain.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component(value = "bookJDBCDaoImpl")
@RequiredArgsConstructor
public class BookJDBCDaoImpl implements BookDao {

    private final JdbcTemplate jdbcTemplate;
    private final BookJDBCMapper bookJDBCMapper;

    @Override
    public Book getById(long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM book WHERE id = ?", bookJDBCMapper, id);
    }

    @Override
    public Book getByTitleAndPublisher(String title, String publisher) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM book WHERE book.title = ? AND book.publisher = ?",
                bookJDBCMapper,
                title,
                publisher);
    }

    @Override
    public Book saveNewBook(Book book) {
        Long idToBeSaved = getNextId();
        jdbcTemplate.update(
                "INSERT INTO book (id, isbn, publisher, title, author_id) VALUES (?, ?, ?, ?, ?)",
                idToBeSaved,
                book.getIsbn(),
                book.getPublisher(),
                book.getTitle(),
                book.getAuthor().getId());

        return getById(idToBeSaved);
    }

    @Override
    public Book updateBook(Long id, Book book) {
        jdbcTemplate.update(
                "UPDATE book SET isbn = ?, publisher = ?, title = ?, author_id = ? WHERE book.id = ?",
                book.getIsbn(),
                book.getPublisher(),
                book.getTitle(),
                book.getAuthor().getId(),
                id);

        return getById(id);
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM book WHERE id = ?", id);
    }

    private Long getNextId() {
        Long nextId = jdbcTemplate.queryForObject("SELECT next_val FROM book_seq", Long.class);
        jdbcTemplate.update("UPDATE book_seq SET next_val = ?", (nextId + 1));
        return nextId;
    }

}
