package com.mauel.user.service;

import com.mauel.user.dto.UserDto;
import com.mauel.user.dto.UserModificationReqDto;
import com.mauel.user.dto.UserRegistrationReqDto;
import com.mauel.user.entity.User;

import java.util.List;

public interface UserService {
    UserDto addUser(UserRegistrationReqDto reqDto);

    UserDto updateUser(Long id, UserModificationReqDto reqDto);

    UserDto getUser(Long id);

    List<UserDto> getUsers();

    void removeUser(Long id);

    default UserDto entityToDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername()).build();
    }
}
