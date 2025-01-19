package com.library_management.api.repository;

import com.library_management.api.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ITransactionRepository extends JpaRepository<Transaction, Long> , JpaSpecificationExecutor<Transaction> {
}
