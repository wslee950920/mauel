package com.mauel.user.service;

import com.mauel.user.dto.UserRegistrationReqDto;
import com.mauel.user.dto.UserRegistrationRespDto;
import com.mauel.user.entity.User;
import com.mauel.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserRegistrationRespDto registerUser(UserRegistrationReqDto reqDto) {
        User user=userRepository.save(dtoToEntity(reqDto));

        return UserRegistrationRespDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .birthDate(user.getBirthDate())
                .sex(user.getSex()).build();
    }
}

