package com.example.jakwywiozebackend.controller;

import com.example.jakwywiozebackend.dto.LoginRequest;
import com.example.jakwywiozebackend.dto.PasswordResetRequest;
import com.example.jakwywiozebackend.dto.RegisterRequest;
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
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest registerRequest){
        return new ResponseEntity<>(userService.register(registerRequest), HttpStatus.OK);
    }
    @PostMapping("/confirm-registration")
    public ResponseEntity<String> register(@RequestParam String token){
        return new ResponseEntity<>(userService.confirmRegistration(token), HttpStatus.OK);
    }
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetRequest resetRequest){
        return new ResponseEntity<>(userService.resetPasswordRequest(resetRequest.getEmail()), HttpStatus.OK);
    }
    @PostMapping("/password-reset-confirmation")
    public ResponseEntity<String> passwordResetConfirmation(@RequestParam String token, @RequestBody @Valid PasswordResetRequest passwordResetRequest){
        return new ResponseEntity<>(userService.resetPassword(token, passwordResetRequest.getPassword()), HttpStatus.OK);
    }
}
