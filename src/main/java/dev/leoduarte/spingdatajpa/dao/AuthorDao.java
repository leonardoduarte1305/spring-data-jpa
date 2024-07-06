package dev.leoduarte.spingdatajpa.dao;

import dev.leoduarte.spingdatajpa.domain.Author;

public interface AuthorDao {

    Author getById(long id);

    Author getByFirstNameAndLastName(String firstName, String lastName);

    Author saveNewAuthor(Author Author);

    Author updateAuthor(Long id, Author Author);

    void deleteById(Long id);
}
