package com.mauel.user.service;

import com.mauel.user.dto.UserRegistrationReqDto;
import com.mauel.user.dto.UserRegistrationRespDto;
import com.mauel.user.entity.User;
import com.mauel.user.exception.DuplicatedException;
import com.mauel.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserRegistrationRespDto registerUser(UserRegistrationReqDto reqDto) {
        User user = dtoToEntity(reqDto);

        Optional<User> optUser;
        optUser=userRepository.findByEmail(user.getEmail());
        if(optUser.isPresent()){
            throw new DuplicatedException("이미 가입된 이메일 입니다.");
        }
        optUser=userRepository.findByUsername(user.getUsername());
        if(optUser.isPresent()){
            throw new DuplicatedException("이미 사용중인 사용자 이름 입니다.");
        }

        return entityToDto(userRepository.save(user));
    }
}

