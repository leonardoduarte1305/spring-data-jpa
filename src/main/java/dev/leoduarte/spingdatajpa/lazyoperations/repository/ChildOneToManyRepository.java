package dev.leoduarte.spingdatajpa.lazyoperations.repository;

import dev.leoduarte.spingdatajpa.lazyoperations.domain.ChildOneToMany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildOneToManyRepository extends JpaRepository<ChildOneToMany, Long> {
}
