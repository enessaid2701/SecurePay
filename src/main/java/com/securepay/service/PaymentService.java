package com.securepay.service;

import com.securepay.dto.PaymentRequest;
import com.securepay.dto.PaymentResponse;
import com.securepay.entity.Customer;
import com.securepay.entity.Payment;
import com.securepay.repository.CustomerRepository;
import com.securepay.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public PaymentResponse placePayment(PaymentRequest paymentRequest) {
        if (paymentRequest.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("The payment amount must be greater than zero.");
        }
        Payment payment = new Payment();
        payment.setCustomerId(paymentRequest.getCustomerId());
        payment.setAmount(paymentRequest.getAmount());
        payment.setPaymentDate(LocalDateTime.now());
        payment = paymentRepository.save(payment);

        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setPaymentDate(payment.getPaymentDate());
        paymentResponse.setAmount(payment.getAmount());
        return paymentResponse;
    }

    public List<PaymentResponse> getAllPaymentsByCustomerId(Long customerId) {
        Customer customer = customerRepository.findById(customerId).get();
        List<Payment> payment = paymentRepository.findByCustomerId(customer.getId());
        List<PaymentResponse> paymentResponses = new ArrayList<>();

        payment.forEach(payments -> {
            PaymentResponse paymentResponse = new PaymentResponse();
            paymentResponse.setPaymentDate(payments.getPaymentDate());
            paymentResponse.setAmount(payments.getAmount());
            paymentResponses.add(paymentResponse);
        });
        return paymentResponses;
    }

    public List<PaymentResponse> getPaymentsByDateInterval(LocalDateTime startDate, LocalDateTime endDate) {
        List<Payment> payment = paymentRepository.findByPaymentDateBetween(startDate, endDate);
        List<PaymentResponse> paymentResponses = new ArrayList<>();

        payment.forEach(payments -> {
            PaymentResponse paymentResponse = new PaymentResponse();
            paymentResponse.setPaymentDate(payments.getPaymentDate());
            paymentResponse.setAmount(payments.getAmount());
            paymentResponse.setCustomerName(customerRepository.findById(payments.getCustomerId()).get().getName());
            paymentResponses.add(paymentResponse);
        });
        return paymentResponses;
    }
}
