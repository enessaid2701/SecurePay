package com.securepay.controller;

import com.securepay.dto.CustomerRequest;
import com.securepay.dto.TokenResponse;
import com.securepay.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Operation(summary = "Customer Registration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer Registration Success",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TokenResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid Parameters",
                    content = @Content)})
    @PostMapping
    public ResponseEntity<TokenResponse> registerCustomer(@Valid @RequestBody CustomerRequest customer) {
        TokenResponse token = customerService.registerCustomer(customer);
        return ResponseEntity.ok(token);
    }

    @Operation(summary = "Customer Update")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer Update Success",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TokenResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid Parameters",
                    content = @Content)})
    @PutMapping
    public ResponseEntity updateCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        customerService.updateCustomer(customerRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
