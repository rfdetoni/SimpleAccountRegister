package com.ogisystems.technicaltest.validations.transaction;

import com.ogisystems.technicaltest.dtos.transaction.TransactionRequestDTO;
import com.ogisystems.technicaltest.exceptions.NotFoundException;
import com.ogisystems.technicaltest.repositories.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TransactionAccountNumberValidationTest {

    @InjectMocks
    private TransactionAccountNumberValidation validator;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRequestDTO dto;

    @Test
    @DisplayName("Should throw NewAccountException for null account number")
    void validate01_nullAccountNumber() {
        Mockito.when(dto.accountNumber()).thenReturn(null);
        assertThrows(NotFoundException.class, () -> validator.validate(dto));
    }

    @Test
    @DisplayName("Should not throw NewAccountException")
    void validate02() {
        given(accountRepository.existsByAccountNumber(anyLong())).willReturn(true);
        assertDoesNotThrow(() -> validator.validate(dto));
    }
}