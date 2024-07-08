package dev.leoduarte.spingdatajpa.dao.hibernate;

import dev.leoduarte.spingdatajpa.domain.hibernate.AuthorHibernate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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
    public AuthorHibernate getByFirstAndLastNameWithNamedQuery(String firstName, String lastName) {
        EntityManager em = getEntityManager();
        TypedQuery<AuthorHibernate> query = em.createNamedQuery("author_find_by_name", AuthorHibernate.class);
        query.setParameter("first_name", firstName);
        query.setParameter("last_name", lastName);

        return query.getSingleResult();
    }

    @Override
    public AuthorHibernate findAuthorByFirstAndLastNameWithCriteria(String firstName, String lastName) {
        try (EntityManager em = getEntityManager()) {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<AuthorHibernate> criteriaQuery = criteriaBuilder.createQuery(AuthorHibernate.class);

            Root<AuthorHibernate> root = criteriaQuery.from(AuthorHibernate.class);
            ParameterExpression<String> firstNameParam = criteriaBuilder.parameter(String.class);
            ParameterExpression<String> lastNameParam = criteriaBuilder.parameter(String.class);

            Predicate firstNamePred = criteriaBuilder.equal(root.get("firstName"), firstNameParam);
            Predicate lastNamePred = criteriaBuilder.equal(root.get("lastName"), lastNameParam);

            criteriaQuery.select(root).where(criteriaBuilder.and(firstNamePred, lastNamePred));

            TypedQuery<AuthorHibernate> typedQuery = em.createQuery(criteriaQuery);
            typedQuery.setParameter(firstNameParam, firstName);
            typedQuery.setParameter(lastNameParam, lastName);

            return typedQuery.getSingleResult();
        }
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
