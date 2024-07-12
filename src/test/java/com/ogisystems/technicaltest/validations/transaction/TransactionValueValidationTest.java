package com.ogisystems.technicaltest.validations.transaction;

import com.ogisystems.technicaltest.dtos.transaction.TransactionRequestDTO;
import com.ogisystems.technicaltest.exceptions.NewTransactionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TransactionValueValidationTest {

    @InjectMocks
    private TransactionValueValidation validator;

    @Mock
    private TransactionRequestDTO dto;

    @Test
    @DisplayName("Should throw NewTransactionException when value is less than zero")
    void validate00() {
       Mockito.when(dto.value()).thenReturn(BigDecimal.valueOf(-1));
       assertThrows(NewTransactionException.class, () -> validator.validate(dto));
    }


    @Test
    @DisplayName("Should throw NewTransactionException when value is equal to zero")
    void validate01() {
        Mockito.when(dto.value()).thenReturn(BigDecimal.ZERO);
        assertThrows(NewTransactionException.class, () -> validator.validate(dto));
    }

    @Test
    @DisplayName("Should not throw NewTransactionException with value greater than zero")
    void validate02() {
        Mockito.when(dto.value()).thenReturn(BigDecimal.ONE);
        assertDoesNotThrow( () -> validator.validate(dto) );
    }

}