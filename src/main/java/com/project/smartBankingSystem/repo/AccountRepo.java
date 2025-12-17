package com.project.smartBankingSystem.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.smartBankingSystem.model.Account;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {
	Optional<Account> findByAccountNumber(String accountNumber);
}
