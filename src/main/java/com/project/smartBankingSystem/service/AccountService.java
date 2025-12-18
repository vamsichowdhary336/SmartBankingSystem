package com.project.smartBankingSystem.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.smartBankingSystem.model.Account;
import com.project.smartBankingSystem.model.Transaction;
import com.project.smartBankingSystem.repo.AccountRepo;
import com.project.smartBankingSystem.repo.TransactionRepo;

@Service
public class AccountService {

	@Autowired
	private AccountRepo accRepo;

	@Autowired
	private TransactionRepo txRepo;

	@Transactional
	public boolean transfer(Account from, Account to, BigDecimal amount) {

		if (from.getBalance().compareTo(amount) < 0) {
			return false;
		}

		// 1️ Update balances
		from.setBalance(from.getBalance().subtract(amount));
		to.setBalance(to.getBalance().add(amount));

		accRepo.save(from);
		accRepo.save(to);

		// 2️ DEBIT transaction
		txRepo.save(
				createTransaction(from, amount, "DEBIT", "Transfer to " + to.getAccountNumber(), from.getBalance()));

		// 3️ CREDIT transaction
		txRepo.save(
				createTransaction(to, amount, "CREDIT", "Transfer from " + from.getAccountNumber(), to.getBalance()));

		return true;
	}

	// Centralized transaction creation (prevents future mistakes)
	private Transaction createTransaction(Account account, BigDecimal amount, String type, String remark,
			BigDecimal balanceAfter) {
		Transaction tx = new Transaction();
		tx.setAccount(account);
		tx.setAmount(amount);
		tx.setType(type);
		tx.setRemark(remark);
		tx.setBalanceAfter(balanceAfter);
		return tx;
	}

	// Statement always returns transaction data
	public List<Transaction> getMiniStatement(Long accountId) {
		return txRepo.findByAccountIdOrderByCreatedAtDesc(accountId);
	}

	public Optional<Account> findByAccountNumber(String accNo) {
		return accRepo.findByAccountNumber(accNo);
	}
}
