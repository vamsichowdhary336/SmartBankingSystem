package com.project.smartBankingSystem.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
	private Long id;
	private String fullName;
	private String email;
	private String mobile;
	private String accountNumber;
	private BigDecimal balance;
}
