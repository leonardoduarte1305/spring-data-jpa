package dev.leoduarte.spingdatajpa.dao.springdatajpa;

import dev.leoduarte.spingdatajpa.dao.BookDao;
import dev.leoduarte.spingdatajpa.domain.springdatajpa.BookSpringJPA;
import dev.leoduarte.spingdatajpa.repository.BookSpringJPARepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpringDataJPADaoImpl implements BookDao<BookSpringJPA> {

    private final BookSpringJPARepository repository;

    @Override
    public BookSpringJPA getById(long id) {
        return repository.getReferenceById(id);
    }

    @Override
    public BookSpringJPA getByTitleAndPublisher(String title, String publisher) {
        return repository.findBookSpringJPAByTitleAndPublisher(title, publisher)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public BookSpringJPA saveNewBook(BookSpringJPA book) {
        return repository.save(book);
    }

    @Override
    public BookSpringJPA updateBook(Long id, BookSpringJPA book) {
        BookSpringJPA found = repository.getReferenceById(id);

        found.setTitle(book.getTitle());
        found.setIsbn(book.getIsbn());
        found.setPublisher(book.getPublisher());
        found.setAuthor(book.getAuthor());

        return repository.save(found);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
