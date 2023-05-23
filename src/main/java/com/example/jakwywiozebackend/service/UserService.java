package com.example.jakwywiozebackend.service;

import com.example.jakwywiozebackend.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();
    UserDto getUser(Long id);
    UserDto createUser(UserDto userDto);
}
