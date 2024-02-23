package com.securepay.dto;

import lombok.Data;

@Data
public class CardRequest
{
    private Long id;
    private String cardNumber;
    private String month;
    private String year;
    private String cvv;

    public CardRequest(String cardNumber, String month, String year, String cvv) {
        this.cardNumber = cardNumber;
        this.month = month;
        this.year = year;
        this.cvv = cvv;
    }

    public CardRequest(Long id, String cardNumber, String month, String year, String cvv) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.month = month;
        this.year = year;
        this.cvv = cvv;
    }
}
