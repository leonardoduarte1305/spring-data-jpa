package dev.leoduarte.spingdatajpa.dao;

import dev.leoduarte.spingdatajpa.domain.Book;

public interface BookDao {

    Book getById(long id);

    Book getByTitleAndPublisher(String title, String publisher);
}
