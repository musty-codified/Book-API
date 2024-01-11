package com.mustycodified.BookApi.controllers.auth;



import com.mustycodified.BookApi.dtos.requests.UserLoginDto;
import com.mustycodified.BookApi.dtos.response.ApiResponse;
import com.mustycodified.BookApi.dtos.response.UserResponseDto;
import com.mustycodified.BookApi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponseDto>> login(@RequestBody UserLoginDto loginDto){
        return ResponseEntity.ok().body( new ApiResponse<>("login successful", true, userService.login(loginDto)));
    }

}
