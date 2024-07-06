package dev.leoduarte.spingdatajpa.dao;

import dev.leoduarte.spingdatajpa.domain.Book;

public interface BookDao {

    Book getById(long id);

    Book getByTitleAndPublisher(String title, String publisher);

    Book saveNewBook(Book book);

    Book updateBook(Long id, Book book);

    void deleteById(Long id);
}
