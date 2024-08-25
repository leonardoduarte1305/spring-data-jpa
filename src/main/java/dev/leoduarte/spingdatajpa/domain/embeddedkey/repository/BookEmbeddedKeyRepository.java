package dev.leoduarte.spingdatajpa.domain.embeddedkey.repository;

import dev.leoduarte.spingdatajpa.domain.embeddedkey.BookEmbeddedKey;
import dev.leoduarte.spingdatajpa.domain.embeddedkey.CompositeEmbeddedKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookEmbeddedKeyRepository extends JpaRepository<BookEmbeddedKey, CompositeEmbeddedKey> {
}
