package com.ogisystems.technicaltest.validations.transaction;

import com.ogisystems.technicaltest.dtos.transaction.TransactionRequestDTO;
import com.ogisystems.technicaltest.exceptions.Errors;
import com.ogisystems.technicaltest.exceptions.NotFoundException;
import com.ogisystems.technicaltest.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionAccountNumberValidation implements TransactionValidator {
    private final AccountRepository accountRepository;

    @Override
    public void validate(TransactionRequestDTO transaction) {
        if( !accountRepository.existsByAccountNumber( transaction.accountNumber() ) ){
            throw new NotFoundException(Errors.NOT_FOUND );
        }
    }
}
