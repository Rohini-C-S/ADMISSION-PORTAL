package com.example.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springapp.model.Admission;

@Repository
public interface AdmissionRepo extends JpaRepository<Admission, Integer> {

}
