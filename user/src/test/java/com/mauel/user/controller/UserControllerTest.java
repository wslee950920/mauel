package com.mauel.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mauel.user.dto.UserRegistrationReqDto;
import com.mauel.user.dto.UserRegistrationRespDto;
import com.mauel.user.entity.Sex;
import com.mauel.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static com.mauel.user.util.ApiDocumentUtils.getDocumentRequest;
import static com.mauel.user.util.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureRestDocs
@WebMvcTest
public class UserControllerTest {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Test
    public void registerUserSuccess() throws Exception {
        //given
        UserRegistrationRespDto respDto = UserRegistrationRespDto.builder()
                .id(1L)
                .email("foo@bar")
                .username("foo")
                .birthDate(LocalDate.of(1995, 9, 20))
                .sex(Sex.MALE).build();

        given(userService.registerUser(any(UserRegistrationReqDto.class))).willReturn(respDto);

        //when
        UserRegistrationReqDto reqDto = new UserRegistrationReqDto();
        reqDto.setEmail("foo@bar");
        reqDto.setUsername("foo");
        reqDto.setBirthDate("950920");
        reqDto.setSex(1);

        ResultActions result = mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reqDto)))
                .andDo(print());

        //then
        result.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.birthDate").value("1995-09-20"))
                .andExpect(jsonPath("$.sex").value("M"))
                .andDo(document("user-register",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseHeaders(
                                headerWithName("Location").description("사용자 정보 uri")
                        ),
                        requestFields(
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("username").description("사용자 이름"),
                                fieldWithPath("birthDate").description("생년월일(주민등록번호 앞자리)"),
                                fieldWithPath("sex").description("성별(주민등록번호 뒷자리 맨 처음 숫자)")
                        ),
                        responseFields(
                                fieldWithPath("id").description("식별번호"),
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("username").description("사용자 이름"),
                                fieldWithPath("birthDate").description("생년월일"),
                                fieldWithPath("sex").description("성별")
                        )));
    }

    @DisplayName("요청값 validation")
    @Test
    public void registerUserFail_Invalid_Email_Form() throws Exception {
        //when
        UserRegistrationReqDto reqDto = new UserRegistrationReqDto();
        reqDto.setEmail("foo$bar");
        reqDto.setUsername("foo");
        reqDto.setBirthDate("950920");
        reqDto.setSex(1);

        ResultActions result = mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reqDto)))
                .andDo(print());

        //then
        result.andExpect(status().isBadRequest());
    }
}
