package com.project.smartBankingSystem.request;

import lombok.Data;

@Data
public class OtpRequest {

	private String email;
	private String otp;
}
