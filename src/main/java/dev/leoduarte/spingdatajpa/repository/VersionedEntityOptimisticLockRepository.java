package dev.leoduarte.spingdatajpa.repository;

import dev.leoduarte.spingdatajpa.domain.version.VersionedEntityOptimisticLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VersionedEntityOptimisticLockRepository extends JpaRepository<VersionedEntityOptimisticLock, Long> {
}
