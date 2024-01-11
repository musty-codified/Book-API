package com.mustycodified.BookApi.controllers;

import com.mustycodified.BookApi.dtos.requests.RegisterUserDto;
import com.mustycodified.BookApi.dtos.response.ApiResponse;
import com.mustycodified.BookApi.dtos.response.UserResponseDto;
import com.mustycodified.BookApi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDto>>registerUser(@Valid @RequestBody RegisterUserDto userDto){
        UserResponseDto userResponseDto = userService.registerUser(userDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{uuid}")
                .buildAndExpand(userResponseDto.getUuid())
                .toUri();
        return ResponseEntity.created(location).body(new ApiResponse<>("Signup successful", true, userResponseDto));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponseDto>> getUser(@PathVariable (name = "userId") String userId){
        return ResponseEntity.ok().body( new ApiResponse<>("Retrieved successfully", true, userService.findUser(userId)));
    }

}
