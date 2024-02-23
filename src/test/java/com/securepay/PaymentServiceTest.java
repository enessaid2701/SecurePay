package com.securepay;

import com.securepay.dto.PaymentRequest;
import com.securepay.service.PaymentService;
import com.securepay.dto.PaymentResponse;
import com.securepay.entity.Customer;
import com.securepay.entity.Payment;
import com.securepay.repository.CustomerRepository;
import com.securepay.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testPlacePayment() {
        PaymentRequest paymentRequest = new PaymentRequest(1L, BigDecimal.valueOf(100));

        Payment payment = new Payment();
        payment.setCustomerId(paymentRequest.getCustomerId());
        payment.setAmount(paymentRequest.getAmount());
        payment.setPaymentDate(LocalDateTime.now());
        when(paymentRepository.save(any())).thenReturn(payment);

        PaymentResponse paymentResponse = paymentService.placePayment(paymentRequest);

        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getPaymentDate());
        assertEquals(paymentRequest.getAmount(), paymentResponse.getAmount());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testGetAllPaymentsByCustomerId() {
        Long customerId = 1L;
        Customer customer = new Customer();
        customer.setId(customerId);
        List<Payment> payments = new ArrayList<>();
        payments.add(new Payment(customerId, BigDecimal.valueOf(50), LocalDateTime.now()));
        payments.add(new Payment(customerId, BigDecimal.valueOf(75), LocalDateTime.now()));

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(paymentRepository.findByCustomerId(customerId)).thenReturn(payments);

        List<PaymentResponse> paymentResponses = paymentService.getAllPaymentsByCustomerId(customerId);

        assertEquals(payments.size(), paymentResponses.size());
        verify(customerRepository, times(1)).findById(customerId);
        verify(paymentRepository, times(1)).findByCustomerId(customerId);
    }

    @Test
    void testGetPaymentsByDateInterval() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(7);
        LocalDateTime endDate = LocalDateTime.now();
        List<Payment> payments = new ArrayList<>();
        payments.add(new Payment(1L, BigDecimal.valueOf(50), LocalDateTime.now().minusDays(5)));
        payments.add(new Payment(2L, BigDecimal.valueOf(75), LocalDateTime.now().minusDays(3)));

        when(paymentRepository.findByPaymentDateBetween(startDate, endDate)).thenReturn(payments);
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(new Customer()));

        List<PaymentResponse> paymentResponses = paymentService.getPaymentsByDateInterval(startDate, endDate);

        assertEquals(payments.size(), paymentResponses.size());
        verify(paymentRepository, times(1)).findByPaymentDateBetween(startDate, endDate);
        verify(customerRepository, times(payments.size())).findById(anyLong());
    }
}
