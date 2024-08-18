package dev.leoduarte.spingdatajpa.domain.interceptors.repository;

import dev.leoduarte.spingdatajpa.domain.interceptors.domain.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
}
