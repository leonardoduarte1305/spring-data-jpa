package dev.leoduarte.spingdatajpa.domain.mappedsuperclass.repository;

import dev.leoduarte.spingdatajpa.domain.mappedsuperclass.OrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Long> {
}
