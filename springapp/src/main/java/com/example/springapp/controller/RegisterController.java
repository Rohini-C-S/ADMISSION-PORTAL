package com.example.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.springapp.dto.RegisterDto;
import com.example.springapp.service.RegisterService;

@RestController
@RequestMapping("/home")
public class RegisterController {

	@Autowired
	private RegisterService registerService;

	public RegisterController(RegisterService registerService) {
		this.registerService = registerService;
	}

	@PostMapping(path = "/register")
	public ResponseEntity<String> saveStudent(@RequestBody RegisterDto registerDto) {
		String email = registerDto.getEmail();
		String phoneNumber = registerDto.getPhoneNumber();

		// Check if email already exists
		if (registerService.checkEmailExists(email)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
		}

		// Check if contact number already exists
		if (registerService.checkphoneNumberExists(phoneNumber)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("phone number already exists");
		}

		String savedStudentId = registerService.addStudent(registerDto);
		return ResponseEntity.ok(savedStudentId);
	}

	@GetMapping(path = "/check-email/{email}")
	public ResponseEntity<Boolean> checkEmailExists(@PathVariable String email) {
		boolean emailExists = registerService.checkEmailExists(email);
		return ResponseEntity.ok(emailExists);
	}

	@GetMapping(path = "/check-phoneNumber/{phoneNumber}")
	public ResponseEntity<Boolean> checkphoneNumberExists(@PathVariable String phoneNumber) {
		boolean phoneNumberExists = registerService.checkphoneNumberExists(phoneNumber);
		return ResponseEntity.ok(phoneNumberExists);

	}

	@PutMapping("/resetpassword/{email}")
	public ResponseEntity<String> updatePasswordByEmailAddress(@PathVariable String email,
			@RequestBody String newPassword) {
		RegisterDto updatedRegisterDto = registerService.updatePassword(email, newPassword);
		if (updatedRegisterDto != null) {
			return ResponseEntity.ok("Password updated successfully");
		} else {
			return ResponseEntity.badRequest().body("Error updating password");
		}
	}
}
