package com.example.springapp.controller;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

import java.util.Optional;

import com.example.springapp.model.Admission;
import com.example.springapp.model.Student;
import com.example.springapp.service.AdmissionService;
import com.example.springapp.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController

public class AdmissionController {

	@Autowired
	private AdmissionService admissionService;

	@Autowired
	private StudentService studentService;

	public AdmissionController(AdmissionService admissionService) {
		this.admissionService = admissionService;
	}

	@PostMapping("/admission")
	public ResponseEntity<Admission> saveStudent(@RequestParam("pdfFile") MultipartFile pdfFile,
			@RequestParam("student") String admissionPayload) {
		try {
			byte[] fileData;
			try (InputStream inputStream = pdfFile.getInputStream()) {
				fileData = inputStream.readAllBytes();
			}
			ObjectMapper objectMapper = new ObjectMapper();
			// Deserialize JSON string into an instance of your Java class
			Admission admission = objectMapper.readValue(admissionPayload, Admission.class);
			// Set the PDF content in the student object
			admission.setRequiredDocuments(fileData);
			Student student = studentService.createStudent(admission.getStudent());
			admission.setStudent(student);
			Admission savedStudentAdmission = admissionService.create(admission);

			return ResponseEntity.status(HttpStatus.CREATED).body(savedStudentAdmission);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping("/acceptRejectApplication/{id}")
	public ResponseEntity<?> updateDocumentStatus(@PathVariable int id, @RequestBody Admission student) {
		String status = student.getStatus();
		Admission updatedStudent = admissionService.updateDocumentStatus(id, status);
		if (updatedStudent != null) {
			return ResponseEntity.ok("Application status updated successfully.");
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating application status.");
		}
	}

	@GetMapping("/getall")
	public ResponseEntity<List<Admission>> getAllStudents() {
		List<Admission> students = admissionService.findAll();
		return ResponseEntity.ok(students);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Admission> getStudentById(@PathVariable int id) {
		Optional<Admission> student = admissionService.findById(id);
		if (student.isPresent()) {
			return ResponseEntity.ok(student.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
