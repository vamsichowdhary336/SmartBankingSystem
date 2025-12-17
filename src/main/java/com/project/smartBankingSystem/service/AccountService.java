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

	public Optional<Account> findByAccountNumber(String accNo) {
		return accRepo.findByAccountNumber(accNo);
	}

	public Optional<Account> findById(Long id) {
		return accRepo.findById(id);
	}

	@Transactional
	public boolean transfer(Account from, Account to, BigDecimal amount) {
		if (from.getBalance().compareTo(amount) < 0)
			return false;
		from.setBalance(from.getBalance().subtract(amount));
		to.setBalance(to.getBalance().add(amount));
		accRepo.save(from);
		accRepo.save(to);

		Transaction t1 = new Transaction();
		t1.setAccount(from);
		t1.setAmount(amount);
		t1.setType("DEBIT");
		t1.setRemark("Transfer to " + to.getAccountNumber());
		txRepo.save(t1);

		Transaction t2 = new Transaction();
		t2.setAccount(to);
		t2.setAmount(amount);
		t2.setType("CREDIT");
		t2.setRemark("Transfer from " + from.getAccountNumber());
		txRepo.save(t2);

		return true;
	}

	public List<Transaction> getMiniStatement(Long accountId) {
		return txRepo.findByAccountIdOrderByCreatedAtDesc(accountId);
	}

}
