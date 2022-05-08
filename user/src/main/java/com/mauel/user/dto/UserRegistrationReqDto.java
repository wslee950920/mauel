package com.mauel.user.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class UserRegistrationReqDto {
    @Email(message = "잘못된 이메일 형식입니다.")
    @NotBlank(message = "잘못된 이메일 형식입니다.")
    private String email;

    @NotBlank(message = "사용자명을 입력해주세요.")
    @Size(max = 100, message = "너무 긴 사용자명 입니다.")
    private String username;
}
