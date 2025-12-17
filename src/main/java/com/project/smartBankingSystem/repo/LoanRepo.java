package com.project.smartBankingSystem.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.smartBankingSystem.model.LoanApplication;

@Repository
public interface LoanRepo extends JpaRepository<LoanApplication, Long> {
	List<LoanApplication> findByUserId(Long userId);
}
