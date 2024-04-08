package com.example.springapp.controller;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springapp.model.Course;
import com.example.springapp.service.CourseService;

@RestController
@RequestMapping("/courses")

public class CourseController {

	@Autowired
	private CourseService courseService;

	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}

	@GetMapping
	public ResponseEntity<List<Course>> getAllCourses() {
		List<Course> courses = courseService.getAllCourse();
		return ResponseEntity.ok(courses);
	}

	@PostMapping
	public ResponseEntity<Boolean> addCourse(@RequestBody Course course) {
		Course createdCourse = courseService.addCourse(course);
		if (createdCourse != null) {
			return ResponseEntity.ok(true);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Course> getCourseById(@PathVariable int id) {
		Optional<Course> optionalCourse = courseService.getCourseById(id);
		return optionalCourse.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Course> updateCourse(@PathVariable int id, @RequestBody Course course) {
		Course updatedCourse = courseService.updateCourse(id, course);
		if (updatedCourse != null) {
			return ResponseEntity.ok(updatedCourse);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteCourse(@PathVariable int id) {
		Optional<Course> optionalCourse = courseService.getCourseById(id);
		if (optionalCourse.isPresent()) {
			Course course = optionalCourse.get();
			courseService.deleteCourse(course);
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