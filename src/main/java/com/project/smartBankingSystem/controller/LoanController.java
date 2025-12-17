package com.project.smartBankingSystem.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.smartBankingSystem.model.LoanApplication;
import com.project.smartBankingSystem.model.User;
import com.project.smartBankingSystem.service.LoanService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/loan")
public class LoanController {

	@Autowired
	private LoanService loanService;

	// APPLY LOAN
	@PostMapping("/apply")
	public ResponseEntity<?> applyLoan(@RequestBody Map<String, String> req, HttpSession session) {

		User user = (User) session.getAttribute("user");
		if (user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login first");
		}

		LoanApplication la = new LoanApplication();
		la.setAmount(new BigDecimal(req.get("amount")));
		la.setUser(user);

		loanService.apply(la);

		return ResponseEntity.status(HttpStatus.CREATED).body("Loan applied successfully");
	}
}
