package dev.leoduarte.spingdatajpa.dao.hibernate;

import dev.leoduarte.spingdatajpa.domain.hibernate.AuthorHibernate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthorHibernateDaoImpl implements AuthorHibernateDao {

    private final EntityManagerFactory emf;

    @Override
    public List<AuthorHibernate> listAuthorByLastNameLike(String lastName) {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<AuthorHibernate> query = em.createQuery("SELECT a FROM AuthorHibernate a WHERE a.lastName LIKE :last_name", AuthorHibernate.class);
            query.setParameter("last_name", "%" + lastName + "%");
            return query.getResultList();
        }
    }

    @Override
    public List<AuthorHibernate> findAll() {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<AuthorHibernate> query = em.createNamedQuery("author_find_all", AuthorHibernate.class);
            return query.getResultList();
        }
    }

    @Override
    public AuthorHibernate getById(long id) {
        return getEntityManager().find(AuthorHibernate.class, id);
    }

    @Override
    public AuthorHibernate getByFirstNameAndLastName(String firstName, String lastName) {
        TypedQuery<AuthorHibernate> query = getEntityManager()
                .createQuery("SELECT a FROM AuthorHibernate a WHERE a.firstName = :first_name AND a.lastName = :last_name",
                        AuthorHibernate.class);
        query.setParameter("first_name", firstName);
        query.setParameter("last_name", lastName);

        return query.getSingleResult();
    }

    @Override
    public AuthorHibernate saveNewAuthorHibernate(AuthorHibernate author) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(author);
        em.flush();
        em.getTransaction().commit();
        return author;
    }

    @Override
    public AuthorHibernate updateAuthorHibernate(Long id, AuthorHibernate author) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.merge(author);
        em.flush();
        em.getTransaction().commit();
        return author;
    }

    @Override
    public void deleteById(Long id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        AuthorHibernate authorHibernate = em.find(AuthorHibernate.class, id);
        em.remove(authorHibernate);
        em.flush();
        em.getTransaction().commit();
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
