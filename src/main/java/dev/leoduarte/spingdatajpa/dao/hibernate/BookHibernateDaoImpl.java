package dev.leoduarte.spingdatajpa.dao.hibernate;

import dev.leoduarte.spingdatajpa.domain.hibernate.BookHibernate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookHibernateDaoImpl implements BookHibernateDao {

    private final EntityManagerFactory emf;

    @Override
    public BookHibernate getById(long id) {
        return getEntityManager().find(BookHibernate.class, id);
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

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
