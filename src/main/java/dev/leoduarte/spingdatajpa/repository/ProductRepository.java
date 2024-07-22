package dev.leoduarte.spingdatajpa.repository;

import dev.leoduarte.spingdatajpa.domain.mappedsuperclass.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
