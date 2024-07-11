package com.ogisystems.technicaltest.validations.transaction;

import com.ogisystems.technicaltest.dtos.account.AccountInfoDTO;
import com.ogisystems.technicaltest.dtos.transaction.TransactionRequestDTO;
import com.ogisystems.technicaltest.validations.account.AccountNotExistsValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionAccountNumberValidation implements TransactionValidator {
    private final AccountNotExistsValidation accountNotExistsValidation;

    @Override
    public void validate(TransactionRequestDTO transaction) {
        accountNotExistsValidation.validate( AccountInfoDTO.builder()
                .accountNumber( transaction.accountNumber() )
                .build() );
    }
}
