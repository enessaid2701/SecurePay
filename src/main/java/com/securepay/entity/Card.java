package com.securepay.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer_card")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card extends BaseEntity {

    @OneToOne(mappedBy = "card", cascade = CascadeType.ALL)
    private Customer customer;

    @NotBlank(message = "Card number is required")
    private String cardNumber;

    @NotBlank(message = "Month is required")
    private String month;

    @NotBlank(message = "Year is required")
    private String year;

    @NotBlank(message = "CVV is required")
    private String cvv;
}

