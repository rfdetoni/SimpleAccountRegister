package com.ogisystems.technicaltest.repositories;

import com.ogisystems.technicaltest.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    Optional<Account> findByAccountNumber(Long accountNumber);

    @Query("SELECT count(a.accountNumber) > 0 FROM Account a WHERE a.accountNumber = :accountNumber")
    boolean existsByAccountNumber(Long accountNumber);
}
