package com.udacity.ecommerce.controllers;

import com.udacity.ecommerce.model.persistence.User;
import com.udacity.ecommerce.model.requests.CreateUserRequest;
import com.udacity.ecommerce.security.Context;
import com.udacity.ecommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/id/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/")
    public ResponseEntity<User> findByUserName(@AuthenticationPrincipal Context context) {
        User user = context.getUser();
        return ResponseEntity.ok(user);
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        User user = userService.create(createUserRequest);
        logger.info("User created with username: {}", user.getUsername());
        return ResponseEntity.ok(user);
    }
}
