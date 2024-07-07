package dev.leoduarte.spingdatajpa.repository;

import dev.leoduarte.spingdatajpa.domain.hibernate.AuthorHibernate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorHibernateRepository extends JpaRepository<AuthorHibernate, Long> {
}
