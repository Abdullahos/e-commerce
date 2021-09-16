package com.example.demo.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.UserRepository;
/**
 * adapted from the supporting material
 * can be found at https://classroom.udacity.com/nanodegrees/nd035/parts/ef7ed085-afaf-4e13-924d-94c9746ed954/modules/c48ee8c7-bbce-4e82-8607-650c5acac401/lessons/6937a8f4-aeb7-4aec-ac7b-37840ecf1765/concepts/f4a66010-25d7-41a2-9266-6b96e5da2646
 * date:16/9/2021
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.emptyList());
    }
}
