package dev.leoduarte.spingdatajpa.lazyoperations.repository;

import dev.leoduarte.spingdatajpa.lazyoperations.domain.EntityWithDataAnnotationMess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityWithDataAnnotationMessRepository extends JpaRepository<EntityWithDataAnnotationMess, Long> {
}
