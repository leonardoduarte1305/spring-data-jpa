package dev.leoduarte.spingdatajpa.dao.hibernate;

import dev.leoduarte.spingdatajpa.domain.hibernate.BookHibernate;

public interface BookHibernateDao {

    BookHibernate getById(long id);

    BookHibernate getByTitleAndPublisher(String title, String publisher);

    BookHibernate saveNewBook(BookHibernate book);

    BookHibernate updateBook(Long id, BookHibernate book);

    void deleteById(Long id);
}
