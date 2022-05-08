package com.mauel.user.controller;

import com.mauel.user.dto.UserDto;
import com.mauel.user.dto.UserModificationReqDto;
import com.mauel.user.dto.UserRegistrationReqDto;
import com.mauel.user.entity.User;
import com.mauel.user.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api")
@RestController
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity addUser(
            @Valid @RequestBody UserRegistrationReqDto reqDto,
            UriComponentsBuilder uriBuilder) {
        User user = userService.addUser(reqDto);

        URI uri = uriBuilder.newInstance()
                .path("/api/user/{id}").buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @PatchMapping("/user/{id}")
    public ResponseEntity updateUser(
            @PathVariable("id") Long id,
            @Valid @RequestBody UserModificationReqDto reqDto,
            UriComponentsBuilder uriBuilder) {
        User user = userService.updateUser(id, reqDto);

        URI uri = uriBuilder.newInstance()
                .path("/api/user/{id}").buildAndExpand(user.getId())
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", uri.toString());

        return ResponseEntity.ok().headers(headers).build();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long id) {
        User user = userService.getUser(id);

        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername()).build();

        return ResponseEntity.ok().body(userDto);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> userDtos = new ArrayList<>();

        for (User user : userService.getUsers()) {
            userDtos.add(UserDto.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .username(user.getUsername()).build());
        }

        return ResponseEntity.ok().body(userDtos);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity removeUser(@PathVariable("id") Long id) {
        userService.removeUser(id);

        return ResponseEntity.noContent().build();
    }
}
