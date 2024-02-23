package com.securepay;

import com.securepay.dto.CardRequest;
import com.securepay.entity.Card;
import com.securepay.entity.Customer;
import com.securepay.repository.CardRepository;
import com.securepay.repository.CustomerRepository;
import com.securepay.service.CardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CardServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardService cardService;

    private static final String ENCODED_CARD_NUMBER = "$2a$10$iDqBU3KHCxjxVmeu0rGXZOaP1OdUS9gfSgMrtFa9N6YxyZ.8R3oze";
    private static final String ENCODED_CVV = "$2a$10$encodedCvv";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateCard() {
        Long customerId = 1L;
        CardRequest cardRequest = new CardRequest(customerId,"1234567890123456", "12", "2025", "123");

        Customer customer = new Customer();
        customer.setId(customerId);

        Card card = new Card();
        card.setCardNumber(cardService.encode(cardRequest.getCardNumber()));
        card.setId(1L);
        card.setMonth(cardRequest.getMonth());
        card.setYear(cardRequest.getYear());
        card.setCvv(cardService.encode(cardRequest.getCvv()));
        card.setCustomer(customer);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(cardRepository.findByCardNumber(anyString())).thenReturn(Optional.empty());
        when(cardRepository.save(any())).thenReturn(card);

        Card createdCard = cardService.createCard(cardRequest);

        assertNotNull(createdCard);
        assertEquals(cardRequest.getMonth(), createdCard.getMonth());
        assertEquals(cardRequest.getYear(), createdCard.getYear());
        assertEquals(customer, createdCard.getCustomer());
        verify(cardRepository, times(1)).save(any(Card.class));
    }

    @Test
    void testUpdateCard() {
        Long customerId = 1L;
        CardRequest cardRequest = new CardRequest(customerId,"9876543210987654", "11", "2024", "321");

        Customer customer = new Customer();
        customer.setId(customerId);
        Card existingCard = new Card();
        existingCard.setCustomer(customer);
        existingCard.setCardNumber(ENCODED_CARD_NUMBER);
        existingCard.setCvv(ENCODED_CVV);
        customer.setCard(existingCard);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(cardRepository.save(any())).thenReturn(existingCard);

        Card updatedCard = cardService.updateCard(cardRequest);

        assertNotNull(updatedCard);
        assertEquals(cardRequest.getMonth(), updatedCard.getMonth());
        assertEquals(cardRequest.getYear(), updatedCard.getYear());
        assertEquals(customer, updatedCard.getCustomer());
        verify(cardRepository, times(1)).save(any(Card.class));
    }
}
