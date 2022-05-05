package com.mauel.user.service;

import com.mauel.user.dto.UserModificationReqDto;
import com.mauel.user.dto.UserRegistrationReqDto;
import com.mauel.user.entity.User;

import java.util.List;

public interface UserService {
    User addUser(UserRegistrationReqDto reqDto);

    User updateUser(Long id, UserModificationReqDto reqDto);

    User getUser(Long id);

    List<User> getUsers();

    void removeUser(Long id);
}
