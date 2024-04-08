package com.example.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springapp.model.Student;

public interface RegisterRepo extends JpaRepository<Student, Integer> {

	Student findByEmail(String email);

	boolean existsByEmail(String email);

	boolean existsByphoneNumber(String phoneNumber);
}