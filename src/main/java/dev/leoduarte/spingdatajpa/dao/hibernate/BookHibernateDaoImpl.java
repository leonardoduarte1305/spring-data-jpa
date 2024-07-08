package dev.leoduarte.spingdatajpa.dao.hibernate;

import dev.leoduarte.spingdatajpa.domain.hibernate.BookHibernate;
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
public class BookHibernateDaoImpl implements BookHibernateDao {

    private final EntityManagerFactory emf;

    @Override
    public BookHibernate getById(long id) {
        return getEntityManager().find(BookHibernate.class, id);
    }

    @Override
    public BookHibernate getByIsbnWithNamedQuery(String isbn) {
        EntityManager em = getEntityManager();
        TypedQuery<BookHibernate> query = em.createNamedQuery("book_find_by_isbn", BookHibernate.class);
        query.setParameter("isbn", isbn);

        return query.getSingleResult();
    }

    @Override
    public BookHibernate getBookByIsbnWithCriteria(String isbn) {
        try (EntityManager em = getEntityManager()) {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<BookHibernate> criteriaQuery = criteriaBuilder.createQuery(BookHibernate.class);

            Root<BookHibernate> root = criteriaQuery.from(BookHibernate.class);
            ParameterExpression<String> isbnParam = criteriaBuilder.parameter(String.class);

            Predicate isbnPred = criteriaBuilder.equal(root.get("isbn"), isbnParam);
            criteriaQuery.select(root).where(criteriaBuilder.and(isbnPred));

            TypedQuery<BookHibernate> typedQuery = em.createQuery(criteriaQuery);
            typedQuery.setParameter(isbnParam, isbn);

            return typedQuery.getSingleResult();
        }
    }

    @Override
    public BookHibernate getByTitleAndPublisher(String title, String publisher) {
        TypedQuery<BookHibernate> query = getEntityManager()
                .createQuery("SELECT b FROM BookHibernate b WHERE b.title = :title AND b.publisher = :publisher",
                        BookHibernate.class);
        query.setParameter("title", title);
        query.setParameter("publisher", publisher);

        return query.getSingleResult();
    }

    @Override
    public BookHibernate saveNewBook(BookHibernate book) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(book);
        em.flush();
        em.getTransaction().commit();
        return book;
    }

    @Override
    public BookHibernate updateBook(Long id, BookHibernate book) {
        EntityManager em = getEntityManager();
        em.joinTransaction();
        em.merge(book);
        em.flush();
        em.clear();
        return em.find(BookHibernate.class, id);
    }

    @Override
    public void deleteById(Long id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        BookHibernate bookHibernate = em.find(BookHibernate.class, id);
        em.remove(bookHibernate);
        em.flush();
        em.getTransaction().commit();
    }

    @Override
    public BookHibernate findByISBN(String isbn) {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<BookHibernate> query = em.createQuery("SELECT b FROM BookHibernate b WHERE b.isbn = :isbn", BookHibernate.class);
            query.setParameter("isbn", isbn);
            return query.getSingleResult();
        }
    }

    @Override
    public List<BookHibernate> findAll() {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<BookHibernate> query = em.createNamedQuery("book_find_all", BookHibernate.class);
            return query.getResultList();
        }
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
