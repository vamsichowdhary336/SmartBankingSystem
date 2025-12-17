package com.project.smartBankingSystem.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.smartBankingSystem.model.Account;
import com.project.smartBankingSystem.model.User;
import com.project.smartBankingSystem.response.TransactionResponse;
import com.project.smartBankingSystem.response.UserResponse;
import com.project.smartBankingSystem.service.AccountService;
import com.project.smartBankingSystem.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/account")
public class AccountController {

	@Autowired
	private AccountService accService;

	@Autowired
	private UserService userService;

	// DASHBOARD
	@GetMapping("/dashboard")
	public ResponseEntity<?> dashboard(HttpSession session) {

		User user = (User) session.getAttribute("user");
		if (user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
		}

		User freshUser = userService.findById(user.getId()).orElseThrow(() -> new RuntimeException("User not found"));
		System.out.println(" get account " + freshUser.getAccount().getAccountNumber());
		System.out.println(" get id " + freshUser.getId());
		if (freshUser.getAccount() == null) {
			return ResponseEntity.badRequest().body("Account not created yet");
		}

		UserResponse response = new UserResponse(freshUser.getId(), freshUser.getFullName(), freshUser.getEmail(),
				freshUser.getMobile(), freshUser.getAccount().getAccountNumber(), freshUser.getAccount().getBalance());

		return ResponseEntity.ok(response);

	}

	// TRANSFER MONEY
	@PostMapping("/transfer")
	public ResponseEntity<?> transfer(@RequestParam String toAccount, @RequestParam BigDecimal amount,
			HttpSession session) {

		User user = (User) session.getAttribute("user");
		if (user == null) {
			return ResponseEntity.status(401).body("User not logged in");
		}

		Account from = user.getAccount();
		var optTo = accService.findByAccountNumber(toAccount);

		if (optTo.isEmpty()) {
			return ResponseEntity.badRequest().body("Destination account not found");
		}

		boolean success = accService.transfer(from, optTo.get(), amount);
		if (!success) {
			return ResponseEntity.badRequest().body("Insufficient balance");
		}

		return ResponseEntity.ok("Transfer successful");
	}

	// STATEMENT
	@GetMapping("/statement")
	public ResponseEntity<?> miniStatement(HttpSession session) {

		User user = (User) session.getAttribute("user");
		if (user == null) {
			return ResponseEntity.status(401).body("User not logged in");
		}

		Account account = user.getAccount();

		return ResponseEntity.ok(accService.getMiniStatement(account.getId()).stream()
				.map(t -> new TransactionResponse(t.getType(), t.getAmount(), t.getCreatedAt(), account.getBalance()

				)).toList());
	}

}
