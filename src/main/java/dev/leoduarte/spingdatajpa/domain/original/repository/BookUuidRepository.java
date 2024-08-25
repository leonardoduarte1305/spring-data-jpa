package dev.leoduarte.spingdatajpa.domain.original.repository;

import dev.leoduarte.spingdatajpa.domain.original.BookUUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookUuidRepository extends JpaRepository<BookUUID, UUID> {
}
