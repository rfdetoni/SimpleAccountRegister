package com.ogisystems.technicaltest.services;

import com.ogisystems.technicaltest.dtos.account.AccountInfoDTO;
import com.ogisystems.technicaltest.dtos.transaction.TransactionRequestDTO;
import com.ogisystems.technicaltest.entities.Account;
import com.ogisystems.technicaltest.entities.Transaction;
import com.ogisystems.technicaltest.exceptions.Errors;
import com.ogisystems.technicaltest.exceptions.InsufficientFundsException;
import com.ogisystems.technicaltest.repositories.TransactionRepository;
import com.ogisystems.technicaltest.validations.transaction.TransactionValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final AccountService accountService;

    private final TransactionRepository transactionRepository;

    private final List<TransactionValidator> transactionValidators;

    @Transactional
    public AccountInfoDTO createTransaction(TransactionRequestDTO transactionRequestDTO) {
        transactionValidators.forEach( transactionValidator -> transactionValidator.validate( transactionRequestDTO ) );

        var account = accountService.getAccountByAccountNumber( transactionRequestDTO.accountNumber() );

        var transaction = createAndValidateTransaction( transactionRequestDTO, account );
        return subtractFromAccountBalance(account, transaction.getAmount() );
    }

    @Transactional
    protected Transaction createAndValidateTransaction(TransactionRequestDTO transactionRequestDTO, Account account) {
        var transaction = transactionRequestDTO.toEntity();
        var totalTransactionValue = calculateTransactionAmount(transactionRequestDTO);

        if (account.getBalance().compareTo(totalTransactionValue) < 0)
            throw new InsufficientFundsException(Errors.INSUFFICIENT_FUNDS);

        transaction.setAccount(account);
        transaction.setAmount(totalTransactionValue);

        return transactionRepository.save(transaction);
    }

    private AccountInfoDTO subtractFromAccountBalance(Account account, BigDecimal totalTransactionValue) {
        return accountService.subtractFromAccountBalance(account, totalTransactionValue);
    }

    private BigDecimal calculateTransactionAmount(TransactionRequestDTO transactionRequestDTO) {
        var transactionAmount = transactionRequestDTO.value();

        var transactionCost = transactionAmount.multiply( transactionRequestDTO.transactionType().getPercentage() );

        return transactionAmount.add( transactionCost );
    }
}