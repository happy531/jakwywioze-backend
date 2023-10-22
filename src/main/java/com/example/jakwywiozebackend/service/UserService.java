package com.example.jakwywiozebackend.service;

import com.example.jakwywiozebackend.dto.LoginRequest;
import com.example.jakwywiozebackend.dto.RegisterRequest;
import com.example.jakwywiozebackend.dto.UserDto;
import com.example.jakwywiozebackend.entity.User;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();
    UserDto getUser(Long id);
    UserDto createUser(UserDto userDto);

    String login(LoginRequest loginRequest);

    String register(RegisterRequest registerRequest);

    void createVerificationTokenForUser(User user, String token);

    String confirmRegistration(String token);

    String resetPassword(String token, String password);
    String resetPasswordRequest(String email);
    User findUserByEmail(String email);
}
