package com.udacity.ecommerce.controllers;

import com.udacity.ecommerce.exception.PasswordException;
import com.udacity.ecommerce.exception.UserException;
import com.udacity.ecommerce.model.persistence.User;
import com.udacity.ecommerce.model.requests.CreateUserRequest;
import com.udacity.ecommerce.security.Context;
import com.udacity.ecommerce.security.UserPrincipal;
import com.udacity.ecommerce.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private User user;
    private CreateUserRequest createUserRequest;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("testuser");
        createUserRequest.setPassword("password123");
        createUserRequest.setConfirmPassword("password123");
    }

    @Test
    public void createUser_happy_path() {
        when(userService.create(createUserRequest)).thenReturn(user);

        ResponseEntity<User> response = userController.createUser(createUserRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(user.getUsername(), response.getBody().getUsername());
    }

    @Test
    public void createUser_password_mismatch() {
        createUserRequest.setConfirmPassword("wrongpassword");
        when(userService.create(createUserRequest)).thenThrow(new PasswordException("Passwords do not match."));

        assertThrows(PasswordException.class, () -> userController.createUser(createUserRequest));
    }

    @Test
    public void findById_happy_path() {
        when(userService.findById(user.getId())).thenReturn(user);

        ResponseEntity<User> response = userController.findById(user.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(user.getUsername(), response.getBody().getUsername());
    }

    @Test
    public void findById_user_not_found() {
        when(userService.findById(user.getId())).thenThrow(new UserException());

        assertThrows(UserException.class, () -> userController.findById(user.getId()));
    }

    @Test
    public void findByUserName_happy_path() {
        Context context = new UserPrincipal(user);

        ResponseEntity<User> response = userController.findByUserName(context);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(user.getUsername(), response.getBody().getUsername());
    }

    @Test
    public void findByUserName_user_not_found() {
        when(userService.findByUserName(user.getUsername())).thenThrow(new UserException());

        Context context = new UserPrincipal(user);
        assertThrows(UserException.class, () -> userController.findByUserName(context));
    }
}
