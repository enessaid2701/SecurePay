package com.securepay.service;

import com.securepay.entity.Token;
import com.securepay.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    public void saveToken(Token token) {
        tokenRepository.save(token);
    }

    public Token getTokenByCustomerId(Long customerId) {
        return tokenRepository.findByCustomerId(customerId);
    }
}
