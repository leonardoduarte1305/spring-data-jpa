package dev.leoduarte.spingdatajpa.domain.original.repository;

import dev.leoduarte.spingdatajpa.domain.original.BookUUIDRFC4122;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookUuidRFC4122Repository extends JpaRepository<BookUUIDRFC4122, UUID> {
}
