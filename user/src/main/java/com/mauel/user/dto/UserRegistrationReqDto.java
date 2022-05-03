package com.mauel.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRegistrationReqDto {
    @Email(message = "잘못된 이메일 형식입니다.")
    @NotBlank(message = "잘못된 이메일 형식입니다.")
    private String email;

    @NotBlank(message = "사용자명을 입력해주세요.")
    @Size(max = 100)
    private String username;

    @NotBlank(message = "주민등록번호 앞자리를 입력해주세요.")
    @Size(min = 6, max = 6)
    private String birthDate;

    @PositiveOrZero(message = "주민등록번호 뒷자리 맨 앞 숫자를 입력해주세요.")
    @Max(value = 9, message = "주민등록번호 뒷자리 맨 앞 숫자를 입력해주세요.")
    private int sex;
}
