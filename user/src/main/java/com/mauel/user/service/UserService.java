package com.mauel.user.service;

import com.mauel.user.dto.UserRegistrationReqDto;
import com.mauel.user.dto.UserRegistrationRespDto;

public interface UserService {
    UserRegistrationRespDto registerUser(UserRegistrationReqDto reqDto);
}
