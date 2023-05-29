package com.example.jakwywiozebackend.service.impl;

import com.example.jakwywiozebackend.dto.UserDto;
import com.example.jakwywiozebackend.entity.User;
import com.example.jakwywiozebackend.mapper.UserMapper;
import com.example.jakwywiozebackend.repository.UserRepository;
import com.example.jakwywiozebackend.service.UserService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
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
        return userMapper.toUserDto(userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found")));
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new EntityExistsException("User already exists");
        }
        return userMapper.toUserDto(userRepository.save(user));
    }
}
