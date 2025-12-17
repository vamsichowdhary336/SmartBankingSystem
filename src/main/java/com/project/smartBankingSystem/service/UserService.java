package com.project.smartBankingSystem.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.smartBankingSystem.model.Account;
import com.project.smartBankingSystem.model.User;
import com.project.smartBankingSystem.repo.AccountRepo;
import com.project.smartBankingSystem.repo.UserRepo;

@Service
public class UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private AccountRepo accRepo;

	public User register(User user) {

		// 1. Save user first
		User savedUser = userRepo.save(user);

		// 2. Create account with MIN balance
		Account acc = new Account();
		acc.setBalance(Account.MIN_BALANCE); // 1000
		acc.setAccountNumber(generateAccountNumber(savedUser.getId()));

		// 3. Set relationship
		acc.setUser(savedUser);
		savedUser.setAccount(acc);

		// 4. Save user (cascade saves account)
		return userRepo.save(savedUser);
	}

	public Optional<User> findByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	public Optional<User> findById(Long id) {
		return userRepo.findById(id);
	}

	public String generateAccountNumber(Long id) {
		return "SB" + String.format("%010d", id);

	}
}
