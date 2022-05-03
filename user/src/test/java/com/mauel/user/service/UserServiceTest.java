package com.mauel.user.service;

import com.mauel.user.dto.UserRegistrationReqDto;
import com.mauel.user.dto.UserRegistrationRespDto;
import com.mauel.user.entity.Sex;
import com.mauel.user.entity.User;
import com.mauel.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Test
    public void convertDtoToEntitySuccess(){
        //given
        UserRegistrationReqDto reqDto = new UserRegistrationReqDto();
        reqDto.setEmail("foo@bar");
        reqDto.setUsername("foo");
        reqDto.setBirthDate("950920");
        reqDto.setSex(1);

        //when
        User user=userService.dtoToEntity(reqDto);

        //then
        assertEquals(reqDto.getEmail(), user.getEmail());
        assertEquals(reqDto.getUsername(), user.getUsername());
        assertEquals(LocalDate.parse("19950920", DateTimeFormatter.ofPattern("yyyyMMdd")), user.getBirthDate());
        assertEquals(Sex.valueOf(reqDto.getSex()), user.getSex());
    }

    @Test
    public void registerUserSuccess(){
        //given
        User user=User.builder()
                .id(1L)
                .email("foo@bar")
                .username("foo")
                .birthDate(LocalDate.of(1995, 9, 20))
                .sex(Sex.MALE).build();

        given(userRepository.save(any(User.class))).willReturn(user);

        //when
        UserRegistrationReqDto reqDto=new UserRegistrationReqDto();
        reqDto.setEmail("foo@bar");
        reqDto.setUsername("foo");
        reqDto.setBirthDate("950920");
        reqDto.setSex(1);

        UserRegistrationRespDto respDto=userService.registerUser(reqDto);

        //then
        assertEquals(user.getId(), respDto.getId());
        assertEquals(user.getEmail(), respDto.getEmail());
        assertEquals(user.getBirthDate(), respDto.getBirthDate());
        assertEquals(user.getSex().getHead(), respDto.getSex());
    }
}
