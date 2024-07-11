package com.ogisystems.technicaltest.validations.transaction;

import com.ogisystems.technicaltest.dtos.transaction.TransactionRequestDTO;
import com.ogisystems.technicaltest.exceptions.Errors;
import com.ogisystems.technicaltest.exceptions.NewTransactionException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransactionValueValidation implements TransactionValidator {
    @Override
    public void validate(TransactionRequestDTO transaction) {
        if (transaction.value().compareTo(BigDecimal.ZERO) <= 0) {
            throw new NewTransactionException(Errors.VALUE_MUST_BE_GREATER_THAN_ZERO);
        }
    }
}
