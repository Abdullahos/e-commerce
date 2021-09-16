package com.example.demo.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.example.demo.model.persistence.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
@Service
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
	 private AuthenticationManager authenticationManager;

	 @Autowired
	 private UserDetailsServiceImpl userDetailsService;

	 @Autowired
     private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
    	try {
    		User credentials = new ObjectMapper().readValue(req.getInputStream(), User.class);
            UserDetails userDetails = userDetailsService.loadUserByUsername(credentials.getUsername());

             if(userDetails!=null) {
                 User retrievedUser = new User();
                 retrievedUser.setUsername(userDetails.getUsername());
                 retrievedUser.setPassword(userDetails.getPassword());
                 //compare the stored hashed password with the entered one(BCryptPasswordEncoder manage that for us)
                 String retrievedHashedPass = retrievedUser.getPassword();
                 String enteredPass = credentials.getPassword();
                 //if passwords matches, authenticate
                 if(bCryptPasswordEncoder.matches(enteredPass,retrievedHashedPass)) {
                     return authenticationManager.authenticate(
                             new UsernamePasswordAuthenticationToken(
                                     credentials.getUsername(),
                                     credentials.getPassword(),
                                     new ArrayList<>()));
                 }
             }
             //if no such username or wrong pass
             throw new Exception("wrong username or pass");
    	} catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    }
    
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        String token = JWT.create()
                .withSubject(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(HMAC512(SecurityConstants.SECRET.getBytes()));
        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
    }
}
