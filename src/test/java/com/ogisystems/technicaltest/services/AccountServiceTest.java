package com.ogisystems.technicaltest.services;

import com.ogisystems.technicaltest.dtos.account.AccountInfoDTO;
import com.ogisystems.technicaltest.entities.Account;
import com.ogisystems.technicaltest.exceptions.NotFoundException;
import com.ogisystems.technicaltest.repositories.AccountRepository;
import com.ogisystems.technicaltest.validations.account.AccountValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Spy
    private Account account;

    @Mock
    private AccountRepository accountRepository;

    @Spy
    private List<AccountValidator> accountValidators = new ArrayList<>();

    @Mock
    private AccountValidator accountValidator1;

    @Mock
    private AccountValidator accountValidator2;

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountInfoDTO dto;

    @Captor
    private ArgumentCaptor<Account> accountCaptor;

    @Test
    @DisplayName("Should call all validators")
    void callAllValidators() {
        accountValidators.add(accountValidator1);
        accountValidators.add(accountValidator2);

        accountService.validateAccount(dto);

        then(accountValidator1).should().validate(dto);
        then(accountValidator2).should().validate(dto);
    }

    @Test
    @DisplayName("Should save an account")
    void saveAccount01() {
        accountService.save(account);
        then(accountRepository).should().save(accountCaptor.capture());
        Account savedAccount = accountCaptor.getValue();
        assertEquals(account.getAccountNumber(), savedAccount.getAccountNumber());
        assertEquals(account.getBalance(), savedAccount.getBalance());
    }

    @Test
    @DisplayName("Should get an AccountInfoDTO")
    void getAccountInfoDTOByAccountNumber01() {
        Long accountNumber = 1L;

        account.setAccountNumber(accountNumber);

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));

        AccountInfoDTO retrievedAccount = accountService.getAccountInfoDTO(accountNumber);

        assertNotNull(retrievedAccount);
        assertEquals(accountNumber, retrievedAccount.getAccountNumber());

    }

    @Test
    @DisplayName("Should throw NotFoundException when account is not found")
    void getAccountInfoDTOByAccountNumber02() {
        Long accountNumber = 1L;
        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> accountService.getAccountInfoDTO(accountNumber));
    }

}