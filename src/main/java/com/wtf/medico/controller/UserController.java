package com.wtf.medico.controller;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
			return theUser;
		} else {
			throw new RuntimeException("Nombre de usuario no valido: " + userId);
		}
	}

	@PostMapping("/users")
	public User addUser(@RequestBody User theUser) {
		validateCharsUsername(theUser.getUsername());
		Boolean validateUsernameSize = validateUsernameMinSize(theUser.getUsername());
		Boolean validatePasswordSize = validateUsernameMinSize(theUser.getPassword());
		Boolean validateNoRepeat = validateUsernameNoRepeat(theUser.getUsername());
		
		if (validateUsernameSize==true) {
			if (validateNoRepeat==true) {
				if (validatePasswordSize==true) {
					theUser.setStatus(true);
					userRepo.save(theUser);
					return theUser;
				}else {
					throw new RuntimeException("La contraseña debe contener al menos 6 caracteres");
				}
			}else {
				throw new RuntimeException("El nombre de usuario ya se encuetra registrado");
			}
		}else {
			throw new RuntimeException("El nombre de usuario debe contener al menos 6 caracteres");
		}
		
	}

	@PutMapping("/users")
	public User updateUser(@RequestBody User theUser) {
		Optional<User> tempUser = userRepo.findById(theUser.getUsername());
		if (tempUser.isPresent()) {
			userRepo.save(theUser);
			return theUser;
		} else {
			throw new RuntimeException("No se encontro el usuario que desea actualizar");
		}
	}

	@DeleteMapping("/users/{userId}")
	public User disableUser(@PathVariable String userId) {
		Optional<User> tempUser = userRepo.findById(userId);
		User theUser = null;
		if (tempUser.isPresent()) {
			theUser = tempUser.get();
			theUser.setStatus(false);
			userRepo.save(theUser);
			return theUser;
		} else {
			throw new RuntimeException("No se encontro el usuario que desea actualizar");
		}
	}

	public Boolean validateUsernameMinSize(String username) {
		if (username.length() < 6) {
			return false;
		}
		return true;
	}
	
	public Boolean validatePasswordMinSize(String username) {
		if (username.length() < 8) {
			return false;
		}
		return true;
	}

	public Boolean validateUsernameNoRepeat(String username) {
		Optional<User> tempUser = userRepo.findById(username);
		if (tempUser.isPresent()) {
			return false;
		}
		return true;
	}
	
	public void validateCharsUsername(String username) {

		Pattern p = Pattern.compile("^(?=\\s*\\S).*$");
		Matcher m = p.matcher(username);
		Boolean result = m.matches();
		System.out.println(result);
	}
	
}
