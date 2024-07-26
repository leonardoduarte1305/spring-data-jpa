package dev.leoduarte.spingdatajpa.repository;

import dev.leoduarte.spingdatajpa.domain.mappedsuperclass.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
}
