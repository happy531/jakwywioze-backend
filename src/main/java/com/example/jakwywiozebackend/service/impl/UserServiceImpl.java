package com.example.jakwywiozebackend.service.impl;

import com.example.jakwywiozebackend.dto.*;
import com.example.jakwywiozebackend.entity.User;
import com.example.jakwywiozebackend.entity.VerificationToken;
import com.example.jakwywiozebackend.mapper.UserMapper;
import com.example.jakwywiozebackend.mapper.VerificationTokenMapper;
import com.example.jakwywiozebackend.repository.UserRepository;
import com.example.jakwywiozebackend.repository.VerificationTokenRepository;
import com.example.jakwywiozebackend.service.UserService;
import com.example.jakwywiozebackend.utils.Events.OnRegistrationCompleteEvent;
import com.example.jakwywiozebackend.utils.Events.PasswordResetEvent;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

import static com.example.jakwywiozebackend.utils.Roles.USER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserMapper userMapper;
    private final VerificationTokenMapper verificationTokenMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final String success = "Success";

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Override
    public List<UserDto> getUsers() {
        return userMapper.toUserDtoList(userRepository.findAll());
    }

    @Override
    public UserDto getUser(Long id) {
        return userMapper.toUserDto(userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found")));
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public User findUserForLoginByUsername(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Login unsuccessful"));
    }
    public UserResponse findUserDtoByUsername(String username) {
        return userMapper.toUserResponse(userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found")));
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new EntityExistsException("User already exists");
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setActive(false);
        return userMapper.toUserDto(userRepository.save(user));
    }

    @Override
    public UserResponse login(LoginRequest loginRequest) {
        User user = findUserForLoginByUsername(loginRequest.getEmail());
        if (!user.isActive()){
            throw new AuthenticationServiceException("User not active");
        }
        try {
            GeneratedTokenDto token = authService.generateToken(loginRequest.getEmail(), loginRequest.getPassword());
            createVerificationTokenForUser(user, token.getToken());
            UserResponse userResponse =  userMapper.toUserResponse(user);
            userResponse.setExp(token.getExp());
            return userResponse;
        } catch (AuthenticationException e){
          throw new AuthenticationServiceException("Login unsuccessful");
        }
    }

    @Override
    public String register(RegisterRequest registerRequest) {
        if(userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
            throw new EntityExistsException("User with this email already exists");
//            return "User with this email already exists";
        }
        UserDto userDto = new UserDto();
        userDto.setEmail(registerRequest.getEmail());
        userDto.setRole(USER);
        userDto.setUsername(registerRequest.getUsername());
        userDto.setPassword(registerRequest.getPassword());
        User user = userMapper.toUser(createUser(userDto));
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user));
        return success;
    }

    @Override
    public void createVerificationTokenForUser(User user, String token) {
        VerificationTokenDto verificationToken = new VerificationTokenDto();
        verificationToken.setToken(token);
        verificationToken.setUser(userMapper.toUserDto(user));
        verificationTokenRepository.save(verificationTokenMapper.toVerificationToken(verificationToken));
    }

    @Override
    public String confirmRegistration(String token) {
        User user = getUserFromToken(token);

        user.setActive(true);
        userRepository.save(user);
        return success;
    }

    public User findUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public String resetPasswordRequest(String email) {
        eventPublisher.publishEvent(new PasswordResetEvent(email));
        return success;
    }

    @Override
    public String resetPassword(String token, String password) {
        User user = getUserFromToken(token);

        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);

        userRepository.save(user);
        return success;
    }

    private User getUserFromToken(String token){
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken == null) {
            throw new EntityNotFoundException("Token invalid");
        }
        Calendar calendar = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - calendar.getTime().getTime()) <= 0) {
            throw new ExpiredJwtException(null, null, "Expired token");
        }
        verificationTokenRepository.delete(verificationToken);
        return verificationToken.getUser();
    }
}
