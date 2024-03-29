package com.example.jakwywiozebackend.service.impl;

import com.example.jakwywiozebackend.config.JwtProvider;
import com.example.jakwywiozebackend.dto.GeneratedTokenDto;
import com.example.jakwywiozebackend.dto.UserDto;
import com.auth0.jwt.JWT;
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

    public GeneratedTokenDto generateToken(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        String token = jwtTokenProvider.generateToken(email);
        return new GeneratedTokenDto(email, token, JWT.decode(token).getExpiresAt().getTime());
    }
    public String generateToken(String username, String password, UserDto userDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        // If authentication is successful, generate JWT
        return jwtTokenProvider.generateToken(userDto);
    }

}