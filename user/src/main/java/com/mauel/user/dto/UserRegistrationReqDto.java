package com.mauel.user.dto;

import com.mauel.user.entity.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRegistrationReqDto {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(max = 100)
    private String username;

    @NotBlank
    @Size(min = 6, max = 6)
    private String birthDate;

    @PositiveOrZero
    @Max(9)
    private int sex;

    private LocalDate parseBirthDate(){
        String full = null;

        if(this.sex == 1 || this.sex == 2 || this.sex == 5 || this.sex == 6){
            // 한국인 1900~, 외국인 1900~
            full = "19"+this.birthDate;
        }else if(this.sex == 3 || this.sex == 4 || this.sex == 7 || this.sex == 8){
            // 한국인 2000~, 외국인 2000~
            full = "20"+this.birthDate;
        }else if(this.sex == 9 || this.sex == 0){
            // 한국인 1800~
            full = "18"+this.birthDate;
        }

        return LocalDate.parse(full, DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    private Sex getSexValue(){
        return Sex.valueOf(this.sex);
    }
}
