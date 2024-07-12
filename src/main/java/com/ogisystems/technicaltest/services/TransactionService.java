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
import java.math.RoundingMode;
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
        validateTransaction(transactionRequestDTO);

        var account = accountService.getAccountByAccountNumber( transactionRequestDTO.accountNumber() );

        var transaction = createAndValidateTransaction( transactionRequestDTO, account );
        return subtractFromAccountBalance(account, transaction.getAmount() );
    }

    protected void validateTransaction(TransactionRequestDTO transactionRequestDTO) {
        transactionValidators.forEach( transactionValidator -> transactionValidator.validate( transactionRequestDTO ) );
    }

    @Transactional
    protected Transaction createAndValidateTransaction(TransactionRequestDTO transactionRequestDTO, Account account) {
        var transaction = transactionRequestDTO.toEntity();
        var totalTransactionValue = calculateTransactionAmount(transactionRequestDTO);

        if (account.getBalance().compareTo(totalTransactionValue) < 0)
            throw new InsufficientFundsException(Errors.INSUFFICIENT_FUNDS);

        transaction.setAccount(account);
        transaction.setAmount(totalTransactionValue);

        return save(transaction);
    }

    protected Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    private AccountInfoDTO subtractFromAccountBalance(Account account, BigDecimal totalTransactionValue) {
        return accountService.subtractFromAccountBalance(account, totalTransactionValue);
    }

    protected BigDecimal calculateTransactionAmount(TransactionRequestDTO transactionRequestDTO) {
        var transactionAmount = transactionRequestDTO.value();

        var transactionCost = transactionAmount.multiply( transactionRequestDTO.transactionType().getPercentage() );

        return transactionAmount.add( transactionCost ).setScale(2, RoundingMode.HALF_UP);
    }
}