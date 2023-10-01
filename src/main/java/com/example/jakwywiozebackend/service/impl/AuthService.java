package com.example.jakwywiozebackend.service.impl;

import com.example.jakwywiozebackend.config.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private JwtProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String generateToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        // If authentication is successful, generate JWT
        return jwtTokenProvider.generateToken(authentication.getName());
    }
}