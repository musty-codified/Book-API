package com.mustycodified.BookApi.services.impl;

import com.mustycodified.BookApi.dtos.requests.RegisterUserDto;
import com.mustycodified.BookApi.dtos.requests.UserLoginDto;
import com.mustycodified.BookApi.dtos.response.UserResponseDto;
import com.mustycodified.BookApi.entities.UserEntity;
import com.mustycodified.BookApi.enums.Roles;
import com.mustycodified.BookApi.exceptions.AuthenticationException;
import com.mustycodified.BookApi.exceptions.NotFoundException;
import com.mustycodified.BookApi.exceptions.ValidationException;
import com.mustycodified.BookApi.repositories.UserRepository;
import com.mustycodified.BookApi.security.CustomUserDetailsService;
import com.mustycodified.BookApi.security.JwtUtils;
import com.mustycodified.BookApi.services.UserService;
import com.mustycodified.BookApi.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final AppUtils appUtil;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtil;

    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public UserResponseDto registerUser(RegisterUserDto userDto) {
        if(userRepository.existsByEmail(userDto.getEmail()))
            throw new ValidationException("User already exists");

        UserEntity newUser = UserEntity.builder()
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .roles(Roles.ROLE_USER.getAuthorities().stream().map(Objects::toString
                ).collect(Collectors.joining(" , ")))
                .uuid(appUtil.generateSerialNumber("usr"))
                .build();

        newUser = userRepository.save(newUser);
        return appUtil.getMapper().convertValue(newUser, UserResponseDto.class);

    }

    @Override
    public UserResponseDto login(UserLoginDto loginDto) {

        try{
            Authentication authentication = authenticationManager
                    .authenticate( new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

            UserResponseDto userResponseDto;
            if (authentication.isAuthenticated()){
             UserEntity user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(()-> new BadCredentialsException("Invalid Login details"));

                log.info("Generating access token.....");
            String accessToken = jwtUtil.generateToken(customUserDetailsService.loadUserByUsername(user.getEmail()));
         userResponseDto = appUtil.getMapper().convertValue(user, UserResponseDto.class);
         userResponseDto.setToken(accessToken);
            } else {
                throw new BadCredentialsException("Invalid email or password");
            }

            return userResponseDto;

        } catch (Exception ex){
            throw new AuthenticationException(ex.getMessage());

        }
    }

    @Override
    public UserResponseDto findUser(String userId) {
      UserEntity user = userRepository.findByUuid(userId)
                .orElseThrow(()-> new NotFoundException("User not found"));

        return appUtil.getMapper().convertValue(user, UserResponseDto.class);
    }
}
