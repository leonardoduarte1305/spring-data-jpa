package dev.leoduarte.spingdatajpa.dao;

public interface AuthorDao<T> {

    T getById(long id);

    T getByFirstNameAndLastName(String firstName, String lastName);

    T saveNewAuthor(T author);

    T updateAuthor(Long id, T author);

    void deleteById(Long id);
}
