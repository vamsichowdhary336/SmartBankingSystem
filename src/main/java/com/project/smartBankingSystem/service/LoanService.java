package com.project.smartBankingSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.smartBankingSystem.model.LoanApplication;
import com.project.smartBankingSystem.repo.LoanRepo;

@Service
public class LoanService {

	@Autowired
	private LoanRepo loanRepo;

	public LoanApplication apply(LoanApplication loan) {
		return loanRepo.save(loan);

	}

	public List<LoanApplication> findByUser(Long userId) {
		return loanRepo.findByUserId(userId);
	}

	public List<LoanApplication> all() {
		return loanRepo.findAll();
	}

	public LoanApplication updateStatus(Long id, String status) {
		var opt = loanRepo.findById(id);
		if (opt.isPresent()) {
			var la = opt.get();
			la.setStatus(status);
			return loanRepo.save(la);
		}
		return null;
	}
}
