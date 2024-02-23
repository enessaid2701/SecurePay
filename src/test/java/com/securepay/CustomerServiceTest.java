package com.securepay;

import com.securepay.auth.JwtUtil;
import com.securepay.dto.CustomerRequest;
import com.securepay.dto.TokenResponse;
import com.securepay.entity.Customer;
import com.securepay.entity.Token;
import com.securepay.repository.CustomerRepository;
import com.securepay.service.CustomerService;
import com.securepay.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRegisterCustomer() {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setEmail("test@example.com");
        customerRequest.setName("Test User");
        customerRequest.setPassword("testPassword");

        Customer customer = new Customer();
        customer.setEmail(customerRequest.getEmail());
        customer.setName(customerRequest.getName());
        String hashedPassword = new BCryptPasswordEncoder().encode(customerRequest.getPassword());
        customer.setPassword(hashedPassword);

        String tokenValue = "generated_token_value";

        when(customerRepository.findByEmail(customerRequest.getEmail())).thenReturn(Optional.empty());
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(jwtUtil.generateToken(customerRequest.getEmail())).thenReturn(tokenValue);

        TokenResponse resultToken = customerService.registerCustomer(customerRequest);

        assertEquals(tokenValue, resultToken.getToken());
        verify(customerRepository, times(1)).findByEmail(customerRequest.getEmail());
        verify(customerRepository, times(1)).save(any(Customer.class));
        verify(jwtUtil, times(1)).generateToken(customerRequest.getEmail());
        verify(tokenService, times(1)).saveToken(any(Token.class));
    }

    @Test
    void testUpdateCustomer() {
        Long customerId = 1L;
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setEmail("test@example.com");
        customerRequest.setName("Updated Name");

        Customer existingCustomer = new Customer();
        existingCustomer.setId(customerId);
        existingCustomer.setEmail("test@example.com");
        existingCustomer.setName("Old Name");

        when(customerRepository.findByEmail(customerRequest.getEmail())).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        customerService.updateCustomer(customerRequest);

        verify(customerRepository, times(1)).findByEmail(customerRequest.getEmail());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }
}
