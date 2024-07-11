package com.ogisystems.technicaltest.repositories;

import com.ogisystems.technicaltest.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

}
