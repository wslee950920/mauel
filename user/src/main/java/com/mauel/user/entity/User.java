package com.mauel.user.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "username")
        }
)
@Entity
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, length = 100)
    private String username;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private char sex;

    public User(Long id, String email, String username, LocalDate birthDate, char sex) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.birthDate = birthDate;
        this.sex = sex;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getUsername() {
        return this.username;
    }

    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    public Sex getSex() {
        return Sex.valueOf(this.sex);
    }

    public static class UserBuilder {
        private Long id;
        private String email;
        private String username;
        private LocalDate birthDate;
        private char sex;

        UserBuilder() {
        }

        public UserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder birthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public UserBuilder sex(Sex sex) {
            this.sex = sex.getHead();
            return this;
        }

        public User build() {
            return new User(id, email, username, birthDate, sex);
        }

        public String toString() {
            return "User.UserBuilder(email=" + this.email + ", username=" + this.username + ", birthDate=" + this.birthDate + ", sex=" + this.sex + ")";
        }
    }
}
