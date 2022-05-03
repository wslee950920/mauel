package com.mauel.user.dto;

import com.mauel.user.entity.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserRegistrationRespDto {
    private Long id;

    private String email;

    private String username;

    private LocalDate birthDate;

    private char sex;

    public static UserRegistrationRespDtoBuilder builder() {
        return new UserRegistrationRespDtoBuilder();
    }

    public static class UserRegistrationRespDtoBuilder {
        private Long id;
        private String email;
        private String username;
        private LocalDate birthDate;
        private char sex;

        UserRegistrationRespDtoBuilder() {
        }

        public UserRegistrationRespDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserRegistrationRespDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserRegistrationRespDtoBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserRegistrationRespDtoBuilder birthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public UserRegistrationRespDtoBuilder sex(Sex sex) {
            this.sex = sex.getHead();
            return this;
        }

        public UserRegistrationRespDto build() {
            return new UserRegistrationRespDto(id, email, username, birthDate, sex);
        }

        public String toString() {
            return "UserRegistrationRespDto.UserRegistrationRespDtoBuilder(id=" + this.id + ", email=" + this.email + ", username=" + this.username + ", birthDate=" + this.birthDate + ", sex=" + this.sex + ")";
        }
    }
}
