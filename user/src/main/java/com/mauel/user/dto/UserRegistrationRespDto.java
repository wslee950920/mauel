package com.mauel.user.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UserRegistrationRespDto {
    private Long id;

    private String email;

    private String username;

    private LocalDate birthDate;

    private char sex;
}
