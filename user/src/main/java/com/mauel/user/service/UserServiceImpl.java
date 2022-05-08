package com.mauel.user.service;

import com.mauel.user.dto.UserModificationReqDto;
import com.mauel.user.dto.UserRegistrationReqDto;
import com.mauel.user.entity.User;
import com.mauel.user.exception.DuplicatedException;
import com.mauel.user.exception.NotFoundException;
import com.mauel.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(UserRegistrationReqDto reqDto) {
        Optional<User> optUser;

        optUser = userRepository.findByEmail(reqDto.getEmail());
        if (optUser.isPresent()) {
            throw new DuplicatedException("이미 가입된 이메일 입니다.");
        }
        optUser = userRepository.findByUsername(reqDto.getUsername());
        if (optUser.isPresent()) {
            throw new DuplicatedException("이미 사용중인 사용자명 입니다.");
        }

        return userRepository.save(User.builder()
                .email(reqDto.getEmail())
                .username(reqDto.getUsername()).build());
    }

    @Override
    public User updateUser(Long id, UserModificationReqDto reqDto) {
        Optional<User> optUser;

        optUser = userRepository.findByUsername(reqDto.getUsername());
        if (optUser.isPresent()) {
            throw new DuplicatedException("이미 사용중인 사용자명 입니다.");
        }
        optUser = userRepository.findById(id);
        if (optUser.isEmpty()) {
            throw new NotFoundException("사용자를 찾을 수 없습니다.");
        }

        User user = optUser.get();
        user.changeUsername(reqDto.getUsername());

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public User getUser(Long id) {
        Optional<User> optUser = userRepository.findById(id);
        if (optUser.isEmpty()) {
            throw new NotFoundException("사용자를 찾을 수 없습니다.");
        }

        return optUser.get();
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void removeUser(Long id) {
        try{
            userRepository.deleteById(id);
        } catch(EmptyResultDataAccessException exception){
            throw new NotFoundException("사용자를 찾을 수 없습니다.", exception);
        }
    }
}

