package dev.leoduarte.spingdatajpa.dao.hibernate;

import dev.leoduarte.spingdatajpa.domain.hibernate.AuthorHibernate;

public interface AuthorHibernateDao {

    AuthorHibernate getById(long id);

    AuthorHibernate getByFirstNameAndLastName(String firstName, String lastName);

    AuthorHibernate saveNewAuthorHibernate(AuthorHibernate author);

    AuthorHibernate updateAuthorHibernate(Long id, AuthorHibernate author);

    void deleteById(Long id);
}
