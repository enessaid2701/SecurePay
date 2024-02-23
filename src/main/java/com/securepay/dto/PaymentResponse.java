package com.securepay.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentResponse {
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private String customerName;
}
