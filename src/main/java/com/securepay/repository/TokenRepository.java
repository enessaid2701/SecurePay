package com.securepay.repository;

import com.securepay.entity.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends CrudRepository<Token, Long> {
    Token findByCustomerId(Long customerId);
}
