package com.example.jakwywiozebackend.mapper;
import com.example.jakwywiozebackend.dto.UserDto;
import com.example.jakwywiozebackend.entity.User;
import org.mapstruct.Mapper;


import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserDto userDto);
    UserDto toUserDto(User user);
    List<User> toUsersList(List<UserDto> userDtos);
    List<UserDto> toUserDtoList(List<User> users);
}
