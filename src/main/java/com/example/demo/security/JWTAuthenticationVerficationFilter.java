package com.example.demo.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
/**
 * adapted from the supporting material
 * can be found at https://classroom.udacity.com/nanodegrees/nd035/parts/ef7ed085-afaf-4e13-924d-94c9746ed954/modules/c48ee8c7-bbce-4e82-8607-650c5acac401/lessons/6937a8f4-aeb7-4aec-ac7b-37840ecf1765/concepts/f4a66010-25d7-41a2-9266-6b96e5da2646
 * date:16/9/2021
 */
@Component
public class JWTAuthenticationVerficationFilter extends BasicAuthenticationFilter {

    public JWTAuthenticationVerficationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }
	@Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) 
    		throws IOException, ServletException {
        String header = req.getHeader(SecurityConstants.HEADER_STRING);

        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            System.out.println();
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {
		String token = req.getHeader(SecurityConstants.HEADER_STRING);
        if (token != null) {
            String user = JWT.require(HMAC512(SecurityConstants.SECRET.getBytes())).build()
                    .verify(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
                    .getSubject();
            if (user != null) {
                logger.debug("access granted");
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
            logger.debug("user is null");
            return null;
        }
        logger.debug("token is null");
        return null;
	}

}
