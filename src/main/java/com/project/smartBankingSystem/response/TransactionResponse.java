package com.project.smartBankingSystem.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionResponse {
	private String type; // CREDIT / DEBIT
	private BigDecimal amount;
	private LocalDateTime date;
	private BigDecimal balance;

}
