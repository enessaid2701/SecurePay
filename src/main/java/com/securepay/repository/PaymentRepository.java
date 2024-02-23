package com.securepay.repository;

import com.securepay.entity.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {

    List<Payment> findByCustomerId(Long customerId);
    List<Payment> findByPaymentDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
