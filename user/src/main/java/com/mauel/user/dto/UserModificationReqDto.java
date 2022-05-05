package com.mauel.user.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserModificationReqDto {
    @NotBlank(message = "사용자명을 입력해주세요.")
    @Size(max = 100, message = "너무 긴 사용자명 입니다.")
    private String username;
}
