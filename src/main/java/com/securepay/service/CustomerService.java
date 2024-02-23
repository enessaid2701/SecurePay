package com.securepay.service;

import com.securepay.auth.JwtUtil;
import com.securepay.dto.TokenResponse;
import com.securepay.entity.Customer;
import com.securepay.dto.CustomerRequest;
import com.securepay.entity.Token;
import com.securepay.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final JwtUtil jwtUtil;

    private final TokenService tokenService;

    public TokenResponse registerCustomer(CustomerRequest customerRequest) {
        if (customerRepository.findByEmail(customerRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("A customer is already registered with this email address.");
        }
        Customer customer = new Customer();
        customer.setEmail(customerRequest.getEmail());
        customer.setName(customerRequest.getName());
        String hashedPassword = new BCryptPasswordEncoder().encode(customerRequest.getPassword());
        customer.setPassword(hashedPassword);

        Customer registeredCustomer = customerRepository.save(customer);

        String tokenValue = jwtUtil.generateToken(registeredCustomer.getEmail());

        Token token = new Token();
        token.setToken(tokenValue);
        token.setCustomer(registeredCustomer);
        tokenService.saveToken(token);

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken(tokenValue);

        return tokenResponse;
    }

    public void updateCustomer(CustomerRequest customerRequest) {
        Customer existingCustomer = customerRepository.findByEmail(customerRequest.getEmail()).get();
        existingCustomer.setName(customerRequest.getName());
        existingCustomer.setEmail(customerRequest.getEmail());
        customerRepository.save(existingCustomer);
    }
}
