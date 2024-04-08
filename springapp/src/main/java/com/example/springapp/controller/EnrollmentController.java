package com.example.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.springapp.model.Enrollment;
import com.example.springapp.service.EnrollmentService;

@RestController
public class EnrollmentController {

	@Autowired
	private EnrollmentService enrollmentService;

	public EnrollmentController(EnrollmentService enrollmentService) {
		this.enrollmentService = enrollmentService;
	}

	@GetMapping("/enrollments")
	public ResponseEntity<List<Enrollment>> getAllEnrollments() {
		List<Enrollment> enrollments = enrollmentService.getAllEnrollment();
		return ResponseEntity.ok(enrollments);
	}

	@PostMapping("/enrollments")
	public ResponseEntity<Enrollment> addEnrollments(@RequestBody Enrollment enroll) {
		return new ResponseEntity<>(enrollmentService.addEnrollment(enroll), HttpStatus.CREATED);
	}

	@GetMapping("/enrollments/{id}")
	public ResponseEntity<Enrollment> getEnrollments(@PathVariable int id) {
		return new ResponseEntity<>(enrollmentService.getEnrollment(id), HttpStatus.OK);
	}

	@PutMapping("/enrollments/{id}")

	public ResponseEntity<Enrollment> updateEnrollments(@PathVariable int id, @RequestBody Enrollment emp) {

		return new ResponseEntity<>(enrollmentService.updateEnrollment(emp, id), HttpStatus.CREATED);

	}

	@DeleteMapping("/enrollments/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
		enrollmentService.deleteEnrollment(id);
		return new ResponseEntity<>("Enrollment with EnrollmentId : " + id + " deleted successfully", HttpStatus.OK);
	}
}
