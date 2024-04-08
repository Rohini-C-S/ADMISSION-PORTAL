package com.example.springapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.springapp.model.Student;
import com.example.springapp.service.UserService;

@RestController

@RequestMapping("/api/auth")
public class LoginController {

	@Autowired
	private final UserService userService;

	public LoginController(UserService userService) {
		this.userService = userService;

	}

	private static final String ADMIN_EMAIL = "admin@gmail.com";
	private static final String ADMIN_PASSWORD = "Admin@123";

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, Object> loginForm) {
		String email = (String) loginForm.get("email");
		String password = (String) loginForm.get("password");

		if (email != null && password != null) {
			if (email.equals(ADMIN_EMAIL) && password.equals(ADMIN_PASSWORD)) {
				// Admin authentication successful

				// Return entered details
				Map<String, Object> response = new HashMap<>();
				response.put("email", email);
				response.put("password", password);
				response.put("role", "ROLE_ADMIN");
				response.put("userId", "admin123");
				return ResponseEntity.ok(response);
			} else {
				Student user = userService.findByEmail(email);
				if (user != null && validateCredentials(email, password, user)) {
					// User authentication successful

					Map<String, Object> response = new HashMap<>();
					response.put("email", email);
					response.put("password", password);
					response.put("role", "ROLE_USER");
					response.put("userId", user.getId());
					return ResponseEntity.ok(response);

				}
			}
		}

		// User authentication failed
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");

	}
	private boolean validateCredentials(String email, String password, Student user) {

		if (user != null && user.getEmail().equals(email) && user.getPassword().equals(password)) {

			return true;

		}

		return false;

	}

}