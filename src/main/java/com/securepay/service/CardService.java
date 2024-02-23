package com.securepay.service;

import com.securepay.dto.CardRequest;
import com.securepay.entity.Card;
import com.securepay.entity.Customer;
import com.securepay.repository.CardRepository;
import com.securepay.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Service
public class CardService
{
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CardRepository cardRepository;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public Card createCard(CardRequest cardRequest) {
        Customer customer = customerRepository.findById(cardRequest.getId()).get();

        if(customer == null) {
            throw new IllegalArgumentException("Customer not found with ID: " + cardRequest.getId());
        }

        Optional<Card> existingCardOptional = cardRepository.findByCardNumber(cardRequest.getCardNumber());
        if (existingCardOptional.isPresent()) {
            throw new IllegalArgumentException("Card number is already associated with another customer");
        }

        Card card = new Card();
        card.setCardNumber(encode(cardRequest.getCardNumber()));
        card.setMonth(cardRequest.getMonth());
        card.setYear(cardRequest.getYear());
        card.setCvv(encode(cardRequest.getCvv()));
        card.setCustomer(customer);

        return cardRepository.save(card);
    }


    public String encode(String input) {
        return encoder.encode(input);
    }

    public Card updateCard(CardRequest cardRequest){
        Customer customer = customerRepository.findById(cardRequest.getId()).get();

        Card card = customer.getCard();
        if (card == null) {
            throw new IllegalArgumentException("Customer has no card associated with it");
        }

        String encryptedCardNumber = encode(cardRequest.getCardNumber());
        String encryptedCvv = encode(cardRequest.getCvv());

        card.setCardNumber(encryptedCardNumber);
        card.setMonth(cardRequest.getMonth());
        card.setYear(cardRequest.getYear());
        card.setCvv(encryptedCvv);

        return cardRepository.save(card);
    }
}
