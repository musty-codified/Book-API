package com.mustycodified.BookApi.services;


import com.mustycodified.BookApi.dtos.requests.RegisterUserDto;
import com.mustycodified.BookApi.dtos.requests.UserLoginDto;
import com.mustycodified.BookApi.dtos.response.UserResponseDto;

public interface UserService {
    UserResponseDto registerUser(RegisterUserDto userDto);
    UserResponseDto login(UserLoginDto userLoginDto);

    UserResponseDto findUser(String userId);

}
