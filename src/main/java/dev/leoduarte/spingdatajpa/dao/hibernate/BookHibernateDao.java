package dev.leoduarte.spingdatajpa.dao.hibernate;

import dev.leoduarte.spingdatajpa.domain.hibernate.BookHibernate;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookHibernateDao {

    List<BookHibernate> findAll();

    BookHibernate findByISBN(String isbn);

    BookHibernate getById(long id);

    BookHibernate getByIsbnWithNamedQuery(String isbn);

    BookHibernate getBookByIsbnWithCriteria(String isbn);

    BookHibernate getBookByIsbnWithNativeQuery(String isbn);

    BookHibernate getByTitleAndPublisher(String title, String publisher);

    BookHibernate saveNewBook(BookHibernate book);

    BookHibernate updateBook(Long id, BookHibernate book);

    void deleteById(Long id);

    void deleteByIdWithFlushModeCommit(Long id);

    void deleteByIdWithFlushModeAuto(Long id);

    List<BookHibernate> findAllBooks(Pageable pageable);
}
