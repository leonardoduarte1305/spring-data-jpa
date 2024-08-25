package dev.leoduarte.spingdatajpa.domain.original.repository;

import dev.leoduarte.spingdatajpa.domain.original.BookNaturalKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookNaturalKeyRepository extends JpaRepository<BookNaturalKey, String> {
}
