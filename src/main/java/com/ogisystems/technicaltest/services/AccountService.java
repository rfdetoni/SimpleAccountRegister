package com.ogisystems.technicaltest.services;

import com.ogisystems.technicaltest.dtos.account.AccountInfoDTO;
import com.ogisystems.technicaltest.entities.Account;
import com.ogisystems.technicaltest.exceptions.Errors;
import com.ogisystems.technicaltest.exceptions.NotFoundException;
import com.ogisystems.technicaltest.repositories.AccountRepository;
import com.ogisystems.technicaltest.validations.account.AccountValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;

    private final List<AccountValidator> accountValidator;

    @Transactional
    public AccountInfoDTO createAccount(AccountInfoDTO account) {
        accountValidator.forEach( validator -> validator.validate( account ) );
        var accountEntity = account.toEntity();
        var newAccount = save(accountEntity);
        return new AccountInfoDTO( newAccount );
    }

    public AccountInfoDTO getAccount(Long accountNumber) {
        var account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow( () -> new NotFoundException( Errors.NOT_FOUND ) );

        return new AccountInfoDTO( account );
    }

    public Account getAccountByAccountNumber(Long accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow( () -> new NotFoundException( Errors.NOT_FOUND ) );
    }

    public AccountInfoDTO subtractFromAccountBalance(Account account, BigDecimal totalTransactionValue) {
        var newBalance = account.getBalance().subtract( totalTransactionValue );
        account.setBalance( newBalance );
        
        var updatedAccount = save( account );
        
        return new AccountInfoDTO( updatedAccount );
    }

    @Transactional
    protected Account save( Account account ) {
        return accountRepository.save( account );
    }
}
