package com.ogisystems.technicaltest.validations.transaction;

import com.ogisystems.technicaltest.dtos.transaction.TransactionRequestDTO;

public interface TransactionValidator {
    void validate(TransactionRequestDTO transaction);
}
