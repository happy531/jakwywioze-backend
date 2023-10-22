package com.example.jakwywiozebackend.mapper;

import com.example.jakwywiozebackend.dto.UserDto;
import com.example.jakwywiozebackend.dto.VerificationTokenDto;
import com.example.jakwywiozebackend.entity.User;
import com.example.jakwywiozebackend.entity.VerificationToken;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VerificationTokenMapper {
    User toUser(UserDto userDto);
    VerificationToken toVerificationToken(VerificationTokenDto verificationTokenDto);
    VerificationTokenDto toVerificationTokenDto(VerificationToken verificationToken);
}
