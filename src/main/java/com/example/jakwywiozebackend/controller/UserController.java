package com.example.jakwywiozebackend.controller;

import com.example.jakwywiozebackend.dto.LoginRequest;
import com.example.jakwywiozebackend.dto.UserDto;
import com.example.jakwywiozebackend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@RequestBody @PathVariable Long id){
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest loginRequest){
        return new ResponseEntity<>(userService.login(loginRequest), HttpStatus.OK);
    }
}
