package com.ogisystems.technicaltest.validations.account;

import com.ogisystems.technicaltest.dtos.account.AccountInfoDTO;
import com.ogisystems.technicaltest.exceptions.Errors;
import com.ogisystems.technicaltest.exceptions.NewAccountException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountBallanceIsNegativeValidation implements AccountValidator {

    public void validate(AccountInfoDTO account) {
        if( account.getBalance().compareTo( BigDecimal.ZERO ) < 0 ) {
            throw new NewAccountException( Errors.INITIAL_BALANCE_CANNOT_BE_NEGATIVE );
        }
    }
}
