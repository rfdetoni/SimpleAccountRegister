package com.ogisystems.technicaltest.dtos.transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ogisystems.technicaltest.entities.Transaction;
import com.ogisystems.technicaltest.enums.TransactionTypes;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TransactionRequestDTO(

        @NotNull
        @JsonProperty("forma_pagamento")
        TransactionTypes transactionType,

        @NotNull
        @JsonProperty("numero_conta")
        Long accountNumber,

        @NotNull
        @JsonProperty("valor")
        BigDecimal value
) {

    public Transaction toEntity(){
        return Transaction.builder()
                .type( transactionType )
                .build();
    }
}
