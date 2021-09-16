package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * class to test user controller layer
 */

public class UserControllerTest {
    private UserController userController;
    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);

    @Before
    public  void init(){
        userController = new UserController();
        TestUtils.injectObjects(userController,"userRepository",userRepository);
        TestUtils.injectObjects(userController,"bCryptPasswordEncoder",bCryptPasswordEncoder);
        TestUtils.injectObjects(userController,"cartRepository",cartRepository);
    }

    /**
     * sanity test for createUser
     */
    @Test
    public void create_User_happy_path(){
        when(bCryptPasswordEncoder.encode("pass12345")).thenReturn("thisHashed");
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setUsername("username");
        userRequest.setPassword("pass12345");
        userRequest.setConfirmPassword("pass12345");

        ResponseEntity<User> userResponseEntity = userController.createUser(userRequest);
        Assertions.assertNotNull(userResponseEntity);
        Assertions.assertEquals(200,userResponseEntity.getStatusCodeValue());

        User user = userResponseEntity.getBody();
        Assertions.assertNotNull(user);
        Assertions.assertEquals(0,user.getId());
        Assertions.assertEquals("username",user.getUsername());
        Assertions.assertEquals("thisHashed",user.getPassword());
    }

    /**
     * test creating customer with password missing any or more password constrains
     */
    @Test
    public void create_user_with_wrong_password_format(){
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setUsername("username");
        userRequest.setPassword("pass");
        final ResponseEntity<User> userResponseEntity = userController.createUser(userRequest);
        Assertions.assertEquals(400,userResponseEntity.getStatusCodeValue());
    }
    /**
     *test finding user by user id
     */
    @Test
    public void findById_happy_path(){
        User user = new User();
        user.setUsername("username");
        user.setPassword("thisHashed");

        when(userRepository.findById(0L)).thenReturn(java.util.Optional.of(user));

        ResponseEntity<User> userResponseEntity = userController.findById(0L);
        Assertions.assertNotNull(userResponseEntity);
        Assertions.assertEquals(200,userResponseEntity.getStatusCodeValue());
        User user1 = userResponseEntity.getBody();
        Assertions.assertEquals(0,user1.getId());
        Assertions.assertEquals("username",user1.getUsername());
        Assertions.assertEquals("thisHashed",user1.getPassword());
    }

    /**
     * testing find user by username
     */
    @Test
    public void find_by_userName_happy_Path(){
        User user = new User();
        user.setUsername("username");
        user.setPassword("thisHashed");
        when(userRepository.findByUsername("username")).thenReturn(user);

        final ResponseEntity<User> userResponseEntity = userController.findByUserName("username");
        Assertions.assertNotNull(userResponseEntity);
        Assertions.assertEquals(200,userResponseEntity.getStatusCodeValue());
        User retrievedUser = userResponseEntity.getBody();
        Assertions.assertEquals("username", retrievedUser.getUsername());
        Assertions.assertEquals("thisHashed", retrievedUser.getPassword());
    }


}
