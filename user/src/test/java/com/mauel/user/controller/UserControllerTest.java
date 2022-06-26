package com.mauel.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mauel.user.dto.UserDto;
import com.mauel.user.dto.UserModificationReqDto;
import com.mauel.user.dto.UserRegistrationReqDto;
import com.mauel.user.entity.User;
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

import static com.mauel.user.util.ApiDocumentUtils.getDocumentRequest;
import static com.mauel.user.util.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@WebMvcTest
public class UserControllerTest {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @DisplayName("회원가입 성공")
    @Test
    public void Should_Created_When_ValidRegistrationRequestBody() throws Exception {
        //given
        UserDto userDto = UserDto.builder()
                .id(1L)
                .email("foo@bar")
                .username("foo").build();

        given(userService.addUser(any(UserRegistrationReqDto.class))).willReturn(userDto);

        //when
        UserRegistrationReqDto reqDto = new UserRegistrationReqDto();
        reqDto.setEmail("foo@bar");
        reqDto.setUsername("foo");

        ResultActions result = mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reqDto)))
                .andDo(print());

        //then
        result.andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/user/"+userDto.getId()))
                .andDo(document("user-register",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("username").description("사용자 이름")
                        ),
                        responseHeaders(
                                headerWithName("Location").description("사용자 정보 uri")
                        )));
    }

    @DisplayName("회원가입 요청 validation 실패")
    @Test
    public void Should_BadRequest_When_InValidRegistrationRequestBody() throws Exception {
        //when
        UserRegistrationReqDto reqDto = new UserRegistrationReqDto();
        reqDto.setEmail("foo$bar"); //이메일 형식 오류
        reqDto.setUsername("foo");

        ResultActions result = mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reqDto)))
                .andDo(print());

        //then
        result.andExpect(status().isBadRequest());
    }

    @DisplayName("사용자명 변경 성공")
    @Test
    public void Should_Ok_When_ValidModificationRequestBody() throws Exception {
        //given
        UserDto userDto = UserDto.builder()
                .id(1L)
                .email("foo@bar")
                .username("foo").build();

        given(userService.updateUser(any(Long.class), any(UserModificationReqDto.class))).willReturn(userDto);

        //when
        UserModificationReqDto reqDto=new UserModificationReqDto();
        reqDto.setUsername("bar");

        ResultActions result=mockMvc.perform(patch("/api/user/{id}", userDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reqDto)))
                .andDo(print());

        //then
        result.andExpect(status().isOk())
                .andExpect(header().string("Location", "/api/user/"+userDto.getId()))
                .andDo(document("user-modify",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("username").description("사용자 이름")
                        ),
                        responseHeaders(
                                headerWithName("Location").description("사용자 정보 uri")
                        )));
    }
}
