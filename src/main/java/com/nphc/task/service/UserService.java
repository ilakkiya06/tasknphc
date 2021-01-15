package com.nphc.task.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nphc.task.entity.User;
import com.nphc.task.helper.CSVHelper;
import com.nphc.task.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	public void save(MultipartFile file) {
		try {
			List<User> tutorials = CSVHelper.csvToUsers(file.getInputStream());
			userRepository.saveAll(tutorials);
		} catch (IOException e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}
	}

	public List<User> getUsers() {
		return userRepository.findAll();
	}

	public User createUser(User user) {
		return userRepository.save(user);
	}

	public Optional<User> getUserById(String id) {
		return userRepository.findById(id);
	}

	public void deleteUserById(String id) {
		userRepository.deleteById(id);
		
	}
}
