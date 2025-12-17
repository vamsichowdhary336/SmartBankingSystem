package com.project.smartBankingSystem.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.smartBankingSystem.model.Transaction;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {

	List<Transaction> findByAccountIdOrderByCreatedAtDesc(Long accountId);

}
