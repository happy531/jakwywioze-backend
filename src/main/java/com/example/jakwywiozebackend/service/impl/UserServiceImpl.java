package com.example.jakwywiozebackend.service.impl;

import com.example.jakwywiozebackend.dto.UserDto;
import com.example.jakwywiozebackend.entity.User;
import com.example.jakwywiozebackend.mapper.UserMapper;
import com.example.jakwywiozebackend.repository.UserRepository;
import com.example.jakwywiozebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public List<UserDto> getUsers() {
        return userMapper.toUserDtoList(userRepository.findAll());
    }

    @Override
    public UserDto getUser(Long id) {
        return userMapper.toUserDto(userRepository.findById(id).orElseThrow(() -> new RuntimeException("null point")));
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        return userMapper.toUserDto(userRepository.save(user));
    }
}
