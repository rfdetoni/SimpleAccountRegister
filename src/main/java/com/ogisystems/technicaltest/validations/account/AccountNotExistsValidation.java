package com.ogisystems.technicaltest.validations.account;

import com.ogisystems.technicaltest.dtos.account.AccountInfoDTO;
import com.ogisystems.technicaltest.exceptions.Errors;
import com.ogisystems.technicaltest.exceptions.NewAccountException;
import com.ogisystems.technicaltest.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountNotExistsValidation implements AccountValidator {

    private final AccountRepository accountRepository;

    public void validate(AccountInfoDTO account) {
        if( accountRepository.existsByAccountNumber( account.getAccountNumber() ) ){
            throw new NewAccountException( Errors.ACCOUNT_ALREADY_EXISTS );
        }
    }
}
