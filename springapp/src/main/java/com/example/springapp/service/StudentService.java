package com.example.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.springapp.model.Student;
import com.example.springapp.repository.StudentRepo;

@Service
public class StudentService {
	@Autowired
	private StudentRepo userRepo;

	public List<Student> getAllStudents() {
		return userRepo.findAll();
	}

	public Student createStudent(Student student) {
		return userRepo.save(student);
	}

	public Optional<Student> getStudentById(int id) {
		return userRepo.findById(id);
	}

	public Student updateStudent(int id, Student student) {
		return userRepo.save(student);
	}

	public void deleteStudent(Student student) {
		userRepo.delete(student);
	}

}
