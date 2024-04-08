package com.example.springapp.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.springapp.model.Profile;
import com.example.springapp.service.ProfileService;

@RestController
@RequestMapping("/home")
public class ProfileController {

	private static ProfileService profileService;

	public ProfileController(ProfileService profileService) {
		this.profileService = profileService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable int id) {
		try {
			Optional<Profile> profile = profileService.getProfileById(id);
			return ResponseEntity.ok(profile);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profile not found");
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateProfile(@PathVariable(value = "id") int id, @RequestBody Profile profile)
			throws RuntimeException {
		try {
			boolean emailExists = profileService.checkEmailExists(profile.getEmail());
			boolean phoneNumberExists = profileService.checkPhoneNumberExists(profile.getPhoneNumber());

			if (emailExists && phoneNumberExists) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Email and phone number already exist");
			} else if (emailExists) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
			} else if (phoneNumberExists) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Phone number already exists");
			}

			Profile updatedProfile = profileService.update(id, profile);
			return ResponseEntity.ok(updatedProfile);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profile not found");
		}
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<?> checkEmailExists(@PathVariable(value = "email") String email) {
		boolean emailExists = profileService.checkEmailExists(email);
		return ResponseEntity.ok(emailExists);
	}

	@GetMapping("/phoneNumber/{phoneNumber}")
	public ResponseEntity<?> checkPhoneNumberExists(@PathVariable(value = "phoneNumber") String phoneNumber) {
		boolean phoneNumberExists = profileService.checkPhoneNumberExists(phoneNumber);
		return ResponseEntity.ok(phoneNumberExists);
	}

}