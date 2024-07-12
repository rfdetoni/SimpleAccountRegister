package com.ogisystems.technicaltest.services;

import com.ogisystems.technicaltest.dtos.transaction.TransactionRequestDTO;
import com.ogisystems.technicaltest.entities.Account;
import com.ogisystems.technicaltest.entities.Transaction;
import com.ogisystems.technicaltest.enums.TransactionTypes;
import com.ogisystems.technicaltest.exceptions.InsufficientFundsException;
import com.ogisystems.technicaltest.repositories.TransactionRepository;
import com.ogisystems.technicaltest.validations.transaction.TransactionValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private Transaction transaction;

    @Mock
    private Account account;

    @Mock
    private TransactionRequestDTO dto;

    @Mock
    private TransactionRepository transactionRepository;

    @Spy
    private List<TransactionValidator> transactionValidators = new ArrayList<>();

    @Mock
    private TransactionValidator transactionValidator1;

    @Mock
    private TransactionValidator transactionValidator2;

    @InjectMocks
    private TransactionService transactionService;

    @Captor
    private ArgumentCaptor<Transaction> transactionCaptor;


    @Test
    @DisplayName("Should call all validators")
    void callAllValidators() {
        transactionValidators.add(transactionValidator1);
        transactionValidators.add(transactionValidator2);

        transactionService.validateTransaction(dto);

        then(transactionValidator1).should().validate(dto);
        then(transactionValidator2).should().validate(dto);
    }

    @Test
    @DisplayName("Should return a BigDecimal value of 103.00 for a Debit transaction")
    void calculateTransactionAmount01() {
        BigDecimal expectedValue = new BigDecimal("103.00");
        when(dto.value()).thenReturn( BigDecimal.valueOf(100) );
        when(dto.transactionType()).thenReturn( TransactionTypes.D );

        BigDecimal actualValue = transactionService.calculateTransactionAmount(dto);

        assertEquals(expectedValue, actualValue);
    }

    @Test
    @DisplayName("Should return a BigDecimal value of 105.00 for a Credit transaction")
    void calculateTransactionAmount02() {
        BigDecimal expectedValue = new BigDecimal("105.00");
        when(dto.value()).thenReturn( BigDecimal.valueOf(100) );
        when(dto.transactionType()).thenReturn( TransactionTypes.C );

        BigDecimal actualValue = transactionService.calculateTransactionAmount(dto);

        assertEquals(expectedValue, actualValue);
    }

    @Test
    @DisplayName("Should return a BigDecimal value of 100.00 for a Pix transaction")
    void calculateTransactionAmount03() {
        BigDecimal expectedValue = BigDecimal.valueOf(100).setScale(2, RoundingMode.HALF_UP);
        when(dto.value()).thenReturn( expectedValue );
        when(dto.transactionType()).thenReturn( TransactionTypes.P );

        BigDecimal actualValue = transactionService.calculateTransactionAmount(dto);

        assertEquals(expectedValue, actualValue);
    }

    @Test
    @DisplayName("Should throw InsufficientFundsException when account balance is less than transaction value")
    void createAndValidateTransaction01() {
        when(dto.value()).thenReturn( BigDecimal.valueOf(100) );
        when(dto.transactionType()).thenReturn( TransactionTypes.D );
        when(account.getBalance()).thenReturn( BigDecimal.valueOf(10) );

        assertThrows(InsufficientFundsException.class, () -> transactionService.createAndValidateTransaction(dto, account));
    }

    @Test
    @DisplayName("Should save a transaction")
    void save() {
        transactionService.save(transaction);
        then(transactionRepository).should().save(transactionCaptor.capture());
        Transaction savedTransaction = transactionCaptor.getValue();
        assertEquals(transaction.getAccount(), savedTransaction.getAccount());
        assertEquals(transaction.getAmount(), savedTransaction.getAmount());
    }

}