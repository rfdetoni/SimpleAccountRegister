package com.ogisystems.technicaltest.validations.account;

import com.ogisystems.technicaltest.dtos.account.AccountInfoDTO;

public interface AccountValidator {
    void validate(AccountInfoDTO account);
}
