package com.example.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springapp.dto.RegisterDto;
import com.example.springapp.model.Student;
import com.example.springapp.repository.RegisterRepo;

@Service
public class RegisterService {

	@Autowired
	private final RegisterRepo regRepo;

	public RegisterService(RegisterRepo regRepo) {
		this.regRepo = regRepo;

	}

	public boolean checkphoneNumberExists(String phoneNumber) {
		return regRepo.existsByphoneNumber(phoneNumber);
	}

	public boolean checkEmailExists(String email) {
		return regRepo.existsByEmail(email);
	}

	public String addStudent(RegisterDto registerDto) {
		Student user = new Student(registerDto.getId(), registerDto.getFirstName(), registerDto.getLastName(),
				registerDto.getEmail(), registerDto.getAddress(), registerDto.getPhoneNumber(),
				registerDto.getPassword());
		regRepo.save(user);
		return user.getFirstName();
	}

	private RegisterDto convertToDto(Student user) {
		RegisterDto registerDto = new RegisterDto();
		registerDto.setPassword(user.getPassword());
		return registerDto;
	}

	public RegisterDto updatePassword(String email, String newPassword) {
		Student user = regRepo.findByEmail(email);
		if (user == null) {
			return null;
		}

		user.setPassword(newPassword);
		regRepo.save(user);
		return convertToDto(user); // Directly return the converted DTO
	}

}