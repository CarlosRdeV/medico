package com.wtf.medico.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wtf.medico.repository.UserRepository;

import com.wtf.medico.entity.User;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserRepository userRepo;

	@GetMapping("/users")
	public List<User> getUsers() {
		return userRepo.findAll();
	}

	@GetMapping("/users/{userId}")
	public User getUser(@PathVariable String userId) {

		Optional<User> tempUser = userRepo.findById(userId);

		User theUser = null;

		if (tempUser.isPresent()) {
			theUser = tempUser.get();
		} else {
			throw new RuntimeException();
		}

		return theUser;
	}
	
	@PostMapping("/users")
	public User addUser(@RequestBody User theUser) {
	
	userRepo.save(theUser);
	
	return theUser;
	}
}
