package com.project.smartBankingSystem.controller;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.smartBankingSystem.model.Account;
import com.project.smartBankingSystem.model.User;
import com.project.smartBankingSystem.repo.UserRepo;
import com.project.smartBankingSystem.request.OtpRequest;
import com.project.smartBankingSystem.service.OtpService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/account")
public class AuthController {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private OtpService otpService;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	// LOGIN
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> req, HttpSession session) {

		String email = req.get("email");
		String password = req.get("password");

		Optional<User> optionalUser = userRepo.findByEmail(email);

		if (optionalUser.isEmpty()) {
			return ResponseEntity.status(401).body("No user found");
		}

		User user = optionalUser.get();

		if (!user.getPassword().trim().equals(password.trim())) {
			return ResponseEntity.status(401).body("Invalid credentials");
		}

		session.setAttribute("user", user); // JSESSIONID
		return ResponseEntity.ok("Login successful");
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody User user) {

		otpService.sendOtp(user.getEmail());

		// store user temporarily in Redis (5 mins)
		redisTemplate.opsForValue().set("PENDING_USER:" + user.getEmail(), user, 5, TimeUnit.MINUTES);

		return ResponseEntity.ok("OTP sent to email");
	}

	// VERIFYING OTP to REGISTER
	@PostMapping("/verify")
	public ResponseEntity<?> verifyOtp(@RequestBody OtpRequest request) {

		// 1. Verify OTP
		if (!otpService.verifyOtp(request.getEmail(), request.getOtp())) {
			return ResponseEntity.badRequest().body("Invalid OTP");
		}

		// 2. Get user from Redis
		User user = (User) redisTemplate.opsForValue().get("PENDING_USER:" + request.getEmail());

		if (user == null) {
			return ResponseEntity.badRequest().body("OTP expired");
		}

		// 3. Save user
		User savedUser = userRepo.save(user);

		// 4. Create & link account
		Account acc = new Account();
		acc.setAccountNumber(generateAccountNumber(savedUser.getId()));
		acc.setBalance(Account.MIN_BALANCE);
		acc.setUser(savedUser);

		savedUser.setAccount(acc);

		// 5. Save again (CASCADE saves account)
		userRepo.save(savedUser);

		// 6. Cleanup Redis
		redisTemplate.delete("PENDING_USER:" + request.getEmail());

		return ResponseEntity.ok("User registered successfully");
	}

	private String generateAccountNumber(Long id) {
		return "SB" + System.currentTimeMillis() + id;
	}

	// LOGOUT
	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpSession session) {
		session.invalidate();
		return ResponseEntity.ok("Logged out successfully");
	}
}
