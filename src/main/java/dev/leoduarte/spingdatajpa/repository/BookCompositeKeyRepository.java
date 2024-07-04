package dev.leoduarte.spingdatajpa.repository;

import dev.leoduarte.spingdatajpa.domain.compositekey.BookCompositeKey;
import dev.leoduarte.spingdatajpa.domain.compositekey.CompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCompositeKeyRepository extends JpaRepository<BookCompositeKey, CompositeKey> {
}
