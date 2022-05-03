package com.mauel.user.service;

import com.mauel.user.dto.UserRegistrationReqDto;
import com.mauel.user.dto.UserRegistrationRespDto;
import com.mauel.user.entity.Sex;
import com.mauel.user.entity.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public interface UserService {
    UserRegistrationRespDto registerUser(UserRegistrationReqDto reqDto);

    default User dtoToEntity(UserRegistrationReqDto reqDto) {
        return User.builder()
                .email(reqDto.getEmail())
                .username(reqDto.getUsername())
                .birthDate(parseBirthDate(reqDto.getBirthDate(), reqDto.getSex()))
                .sex(Sex.valueOf(reqDto.getSex())).build();
    }

    default UserRegistrationRespDto entityToDto(User user){
        return UserRegistrationRespDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .birthDate(user.getBirthDate())
                .sex(user.getSex()).build();
    }

    private LocalDate parseBirthDate(String birthDate, int sex) {
        String full;

        if (sex == 1 || sex == 2 || sex == 5 || sex == 6) {
            // 한국인 1900~, 외국인 1900~
            full = "19" + birthDate;
        } else if (sex == 3 || sex == 4 || sex == 7 || sex == 8) {
            // 한국인 2000~, 외국인 2000~
            full = "20" + birthDate;
        } else if (sex == 9 || sex == 0) {
            // 한국인 1800~
            full = "18" + birthDate;
        } else {
            throw new AssertionError("Invalid sex value : " + sex);
        }

        return LocalDate.parse(full, DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
}
