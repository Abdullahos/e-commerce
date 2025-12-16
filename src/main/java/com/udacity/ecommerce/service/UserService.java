package com.udacity.ecommerce.service;

import com.udacity.ecommerce.exception.PasswordException;
import com.udacity.ecommerce.exception.UserException;
import com.udacity.ecommerce.model.persistence.Cart;
import com.udacity.ecommerce.model.persistence.User;
import com.udacity.ecommerce.model.persistence.repositories.CartRepository;
import com.udacity.ecommerce.model.persistence.repositories.UserRepository;
import com.udacity.ecommerce.model.requests.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final CartRepository cartRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(UserException::new);
    }

    public User findByUserName(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserException();
        }
        return user;
    }

    public User create(CreateUserRequest request) {
        if (userRepository.findByUsername(request.getUsername()) != null) {
            throw new UserException("Username is already taken.");
        }
        if (request.getPassword().length() < 7) {
            throw new PasswordException("Password must be at least 7 characters long.");
        }
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new PasswordException("Passwords do not match.");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));

        Cart cart = new Cart();
        cartRepository.save(cart);
        user.setCart(cart);

        return userRepository.save(user);
    }
}
