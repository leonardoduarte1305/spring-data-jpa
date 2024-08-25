package dev.leoduarte.spingdatajpa.domain.springdatajpa.services;


import dev.leoduarte.spingdatajpa.domain.springdatajpa.BookSpringJPA;

public interface BookJPAService {

    BookSpringJPA save(BookSpringJPA bookSpringJPA);

    BookSpringJPA updateBook(Long id, Long author);
}
