package dev.leoduarte.spingdatajpa.lazyoperations.repository;

import dev.leoduarte.spingdatajpa.lazyoperations.domain.BaseEntityLazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseEntityLazyRepository extends JpaRepository<BaseEntityLazy, Long> {

    @Query("SELECT b FROM BaseEntityLazy b " +
            "JOIN FETCH b.childManyToMany " +
            "JOIN FETCH b.childManyToOne " +
            "JOIN FETCH b.childOneToMany " +
            "JOIN FETCH b.childOneToOne " +
            "ORDER BY b.id")
    Page<BaseEntityLazy> findWithLimit(Pageable total);
}
