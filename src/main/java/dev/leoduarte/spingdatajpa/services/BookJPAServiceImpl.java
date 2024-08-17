package dev.leoduarte.spingdatajpa.services;

import dev.leoduarte.spingdatajpa.domain.springdatajpa.BookSpringJPA;
import dev.leoduarte.spingdatajpa.repository.BookSpringJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookJPAServiceImpl implements BookJPAService {

    private final BookSpringJPARepository repository;

    @Override
    public BookSpringJPA save(BookSpringJPA bookSpringJPA) {
        return repository.saveAndFlush(bookSpringJPA);
    }

    @Transactional
    @Override
    public BookSpringJPA updateBook(Long id, Long author) {
        BookSpringJPA book = repository.findById(id).orElseThrow();
        book.setAuthor(author);
        return repository.saveAndFlush(book);
    }
}
