package com.example.springapp.controller;

import com.example.springapp.model.Student;
import com.example.springapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/students")

public class StudentController {

	@Autowired
	private final StudentService studentService;

	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	@GetMapping
	public ResponseEntity<List<Student>> getAllStudents() {
		List<Student> students = studentService.getAllStudents();
		return ResponseEntity.ok(students);
	}

	@PostMapping
	public ResponseEntity<String> createStudent(@RequestBody Student student) {
		Student createdStudent = studentService.createStudent(student);
		if (createdStudent != null) {
			return ResponseEntity.ok("Student created successfully");
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Student cannot be created");
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable int id) {
		Optional<Student> optionalStudent = studentService.getStudentById(id);
		return optionalStudent.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student studentDetails) {
		Student updatedStudent = studentService.updateStudent(id, studentDetails);
		if (updatedStudent != null) {
			return ResponseEntity.ok(updatedStudent);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteStudent(@PathVariable int id) {
		Optional<Student> optionalStudent = studentService.getStudentById(id);
		if (optionalStudent.isPresent()) {
			Student student = optionalStudent.get();
			studentService.deleteStudent(student);
			Map<String, Boolean> response = new HashMap<>();
			response.put("deleted", Boolean.TRUE);
			return ResponseEntity.ok(response);
		} else {
			Map<String, Boolean> response = new HashMap<>();
			response.put("deleted", Boolean.FALSE);
			return ResponseEntity.ok(response);
		}
	}
}