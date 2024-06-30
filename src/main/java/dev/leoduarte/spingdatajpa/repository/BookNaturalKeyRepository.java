package dev.leoduarte.spingdatajpa.repository;

import dev.leoduarte.spingdatajpa.domain.BookNaturalKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookNaturalKeyRepository extends JpaRepository<BookNaturalKey, String> {
}
