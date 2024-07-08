package dev.leoduarte.spingdatajpa.dao;

public interface BookDao<T> {

    T getById(long id);

    T getByTitleAndPublisher(String title, String publisher);

    T saveNewBook(T book);

    T updateBook(Long id, T book);

    void deleteById(Long id);
}
