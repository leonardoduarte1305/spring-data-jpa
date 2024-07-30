package dev.leoduarte.spingdatajpa.domain.problementities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookNPlusOneRepository extends JpaRepository<BookNPlusOne, Long> {
}
