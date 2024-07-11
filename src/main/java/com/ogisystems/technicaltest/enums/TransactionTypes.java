package com.ogisystems.technicaltest.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ogisystems.technicaltest.deserializer.TransactionTypeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@JsonDeserialize(using = TransactionTypeDeserializer.class)
public enum TransactionTypes {
    P("Pix", new BigDecimal("0.0") ),
    C("Credit Card", new BigDecimal("0.05") ),
    D("Debit Card", new BigDecimal("0.03") );

    private final String name;
    private final BigDecimal percentage;
}
