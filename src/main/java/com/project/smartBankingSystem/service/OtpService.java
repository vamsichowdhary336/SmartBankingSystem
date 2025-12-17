package com.project.smartBankingSystem.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.project.smartBankingSystem.util.OtpUtil;

@Service
public class OtpService {

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Autowired
	private EmailService emailService;

	public void sendOtp(String email) {
		String otp = OtpUtil.generate();
		redisTemplate.opsForValue().set("OTP:" + email, otp, 5, TimeUnit.MINUTES);
		emailService.sendOtp(email, otp);
	}

	public boolean verifyOtp(String email, String inputOtp) {

		String savedOtp = redisTemplate.opsForValue().get("OTP:" + email);

		if (savedOtp == null)
			return false;
		if (!savedOtp.equals(inputOtp))
			return false;

		redisTemplate.delete("OTP:" + email);
		return true;
	}

}
