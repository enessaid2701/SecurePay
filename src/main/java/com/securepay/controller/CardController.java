package com.securepay.controller;

import com.securepay.dto.CardRequest;
import com.securepay.dto.TokenResponse;
import com.securepay.entity.Card;
import com.securepay.service.CardService;
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
@RequestMapping("/api/card")
public class CardController {

    @Autowired
    private CardService cardService;

    @Operation(summary = "Card Registration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card Registration Success",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TokenResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid Parameters",
                    content = @Content)})
    @PostMapping
    public ResponseEntity createCard(@Valid @RequestBody CardRequest cardRequest) {
        cardService.createCard(cardRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Card Update")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card Update Success",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TokenResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid Parameters",
                    content = @Content)})
    @PutMapping
    public ResponseEntity updateCard(@Valid @RequestBody CardRequest cardRequest) {
        cardService.updateCard(cardRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
