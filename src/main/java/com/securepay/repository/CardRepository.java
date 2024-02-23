package com.securepay.repository;

import com.securepay.entity.Card;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends CrudRepository<Card, Long> {
    Optional<Card> findByCardNumber(String cardNumber);
}
