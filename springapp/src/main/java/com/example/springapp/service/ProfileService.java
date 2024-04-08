package com.example.springapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.springapp.model.Profile;
import com.example.springapp.repository.ProfileRepo;

@Service
public class ProfileService {

	@Autowired
	private static ProfileRepo repo;

	public ProfileService(ProfileRepo repo) {
		this.repo = repo;
	}

	public Optional<Profile> getProfileById(int id) {
		return repo.findById(id);

	}

	public Profile update(int id, Profile profile) {
		Profile existingProfile = repo.findById(id).orElseThrow();

		existingProfile.setFirstName(profile.getFirstName());
		existingProfile.setLastName(profile.getLastName());
		existingProfile.setEmail(profile.getEmail());
		existingProfile.setAddress(profile.getAddress());
		existingProfile.setPhoneNumber(profile.getPhoneNumber());

		return repo.save(existingProfile);
	}

	public boolean checkEmailExists(String email) {
		Profile existingProfile = repo.findByEmail(email);
		return existingProfile != null;
	}

	public boolean checkPhoneNumberExists(String phoneNumber) {
		Profile existingProfile = repo.findByPhoneNumber(phoneNumber);
		return existingProfile != null;
	}

}