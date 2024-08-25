package dev.leoduarte.spingdatajpa.domain.hibernate.repository;

import dev.leoduarte.spingdatajpa.domain.hibernate.BookHibernate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookHibernateRepository extends JpaRepository<BookHibernate, Long> {
}
