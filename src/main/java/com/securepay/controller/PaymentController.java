package com.securepay.controller;

import com.securepay.dto.PaymentRequest;
import com.securepay.dto.PaymentResponse;
import com.securepay.dto.TokenResponse;
import com.securepay.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Operation(summary = "Place Payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Place Payment Success",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TokenResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid Parameters",
                    content = @Content)})
    @PostMapping
    public ResponseEntity<PaymentResponse> placePayment(@Valid @RequestBody PaymentRequest paymentRequest) {
        PaymentResponse placedPayment = paymentService.placePayment(paymentRequest);
        return new ResponseEntity<>(placedPayment, HttpStatus.CREATED);
    }

    @Operation(summary = "Get All Payment By CustomerId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get All Payment By CustomerId Success",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TokenResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid Parameters",
                    content = @Content)})
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<PaymentResponse>> getAllPaymentsByCustomerId(@Valid @PathVariable Long customerId) {
        List<PaymentResponse> payments = paymentService.getAllPaymentsByCustomerId(customerId);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @Operation(summary = "Get All Payment By Date Interval")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Place Payment Success",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TokenResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid Parameters",
                    content = @Content)})
    @GetMapping
    public ResponseEntity<List<PaymentResponse>> getPaymentsByDateInterval(
            @Valid
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<PaymentResponse> payments = paymentService.getPaymentsByDateInterval(startDate, endDate);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }
}
