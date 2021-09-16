package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	private static Logger logger = LogManager.getLogger(UserController.class);

	@GetMapping("/id/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		Optional<User> user = userRepository.findById(id);
		return user==null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user.get());
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<User> findByUserName(@PathVariable String username) {
		User user = userRepository.findByUsername(username);
		return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
	}
	
	@PostMapping("/create")
	public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
		User user = new User();
		user.setUsername(createUserRequest.getUsername());
		final User userByUsername = userRepository.findByUsername(user.getUsername());
		/*
		if(userByUsername!=null){
			logger.warn("try different username!");
			return ResponseEntity.badRequest().build();
		}
		*/

		//checking the password constrains
		if(createUserRequest.getPassword().length()<7) {
			logger.warn("password length must be at least 7!");
			return ResponseEntity.badRequest().build();
		}
		if(!createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword())){
			logger.warn("passwords don't match!");
			return ResponseEntity.badRequest().build();
		}
		//valid password structure
		String hashedPass = bCryptPasswordEncoder.encode(createUserRequest.getPassword());
		user.setPassword(hashedPass);

		//set an empty cart for the user
		Cart cart = new Cart();
		cartRepository.save(cart);
		user.setCart(cart);
		userRepository.save(user);

		logger.info("account created!");
		return ResponseEntity.ok(user);
	}
}
