package com.mauel.user.controller;

import com.mauel.user.dto.UserRegistrationReqDto;
import com.mauel.user.dto.UserRegistrationRespDto;
import com.mauel.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RequestMapping("/api")
@RestController
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<UserRegistrationRespDto> registerUser(
            @Valid @RequestBody UserRegistrationReqDto reqDto,
            UriComponentsBuilder uriBuilder) {
        UserRegistrationRespDto respDto = userService.registerUser(reqDto);

        URI uri=uriBuilder.newInstance()
                .path("/api/user/{id}").buildAndExpand(respDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(respDto);
    }
}
