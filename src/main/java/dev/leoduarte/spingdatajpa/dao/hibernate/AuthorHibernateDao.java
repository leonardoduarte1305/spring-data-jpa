package dev.leoduarte.spingdatajpa.dao.hibernate;

import dev.leoduarte.spingdatajpa.domain.hibernate.AuthorHibernate;

import java.util.List;

public interface AuthorHibernateDao {

    List<AuthorHibernate> findAll();

    List<AuthorHibernate> listAuthorByLastNameLike(String lastName);

    AuthorHibernate getById(long id);

    AuthorHibernate getByFirstAndLastNameWithNamedQuery(String firstName, String lastName);

    AuthorHibernate getByFirstNameAndLastName(String firstName, String lastName);

    AuthorHibernate saveNewAuthorHibernate(AuthorHibernate author);

    AuthorHibernate updateAuthorHibernate(Long id, AuthorHibernate author);

    void deleteById(Long id);
}
