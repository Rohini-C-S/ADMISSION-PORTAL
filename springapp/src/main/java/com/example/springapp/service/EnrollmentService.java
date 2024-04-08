package com.example.springapp.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.example.springapp.model.Enrollment;
import com.example.springapp.repository.EnrollmentRepo;

@Service
public class EnrollmentService {

	private static EnrollmentRepo enrollmentRepo;

	public EnrollmentService(EnrollmentRepo enrollmentRepo) {
		this.enrollmentRepo = enrollmentRepo;
	}

	public static List<Enrollment> getAllEnrollment() {
		return enrollmentRepo.findAll();
	}

	public Enrollment addEnrollment(Enrollment emp) {
		return enrollmentRepo.save(emp);
	}

	public Enrollment getEnrollment(int id) {
		Optional<Enrollment> enrolls = enrollmentRepo.findById(id);

		if (enrolls.isPresent()) {
			return enrolls.get();
		}
		return null;
	}

	public Enrollment updateEnrollment(Enrollment emp, int id) {

		Optional<Enrollment> optionalEnrollment = enrollmentRepo.findById(id);

		if (optionalEnrollment.isPresent()) {

			Enrollment existingEnrollment = optionalEnrollment.get();
			existingEnrollment.setUser(emp.getUser());
			existingEnrollment.setCourse(emp.getCourse());
			return enrollmentRepo.save(existingEnrollment);

		}

		return new Enrollment();

	}

	public void deleteEnrollment(int id) {
		enrollmentRepo.deleteById(id);
	}

}