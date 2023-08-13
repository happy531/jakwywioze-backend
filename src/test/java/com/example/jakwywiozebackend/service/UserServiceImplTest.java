package com.example.jakwywiozebackend.service;

import com.example.jakwywiozebackend.dto.UserDto;
import com.example.jakwywiozebackend.entity.User;
import com.example.jakwywiozebackend.mapper.UserMapper;
import com.example.jakwywiozebackend.repository.UserRepository;
import com.example.jakwywiozebackend.service.impl.UserServiceImpl;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUsers() {
        List<User> users = List.of(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);
        List<UserDto> userDtos = List.of(new UserDto(), new UserDto());
        when(userMapper.toUserDtoList(users)).thenReturn(userDtos);

        List<UserDto> result = userService.getUsers();
        assertEquals(userDtos.size(), result.size());
    }

    @Test
    public void testGetUser() {
        Long id = 1L;
        User user = new User();
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(java.util.Optional.of(user));
        UserDto userDto = new UserDto();
        userDto.setId(id);
        when(userMapper.toUserDto(user)).thenReturn(userDto);

        UserDto result = userService.getUser(id);
        assertEquals(id, result.getId());
    }

    @Test
    public void testCreateUser() {
        Long id = 1L;
        String username = "test";
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setUsername(username);
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        when(userMapper.toUser(userDto)).thenReturn(user);
        when(userRepository.findByUsername(username)).thenReturn(java.util.Optional.empty());
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toUserDto(user)).thenReturn(userDto);

        UserDto result = userService.createUser(userDto);
        assertEquals(id, result.getId());
    }

    @Test
    public void testCreateUserAlreadyExists() {
        Long id = 1L;
        String username = "test";
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setUsername(username);
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        when(userMapper.toUser(userDto)).thenReturn(user);
        when(userRepository.findByUsername(username)).thenReturn(java.util.Optional.of(user));

        assertThrows(EntityExistsException.class, () -> userService.createUser(userDto));
    }
}
