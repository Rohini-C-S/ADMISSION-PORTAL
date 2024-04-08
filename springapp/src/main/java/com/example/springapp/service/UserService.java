package com.example.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springapp.model.Student;
import com.example.springapp.repository.UserRepo;

@Service
public class UserService {

	@Autowired
	private UserRepo userRepository;

	public UserService(UserRepo userRepository) {
		this.userRepository = userRepository;

	}

	public Student findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

}