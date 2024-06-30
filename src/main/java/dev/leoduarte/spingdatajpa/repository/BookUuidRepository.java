package dev.leoduarte.spingdatajpa.repository;

import dev.leoduarte.spingdatajpa.domain.BookUUID;
import dev.leoduarte.spingdatajpa.domain.BookUUIDRFC4122;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookUuidRepository extends JpaRepository<BookUUID, UUID> {
}
