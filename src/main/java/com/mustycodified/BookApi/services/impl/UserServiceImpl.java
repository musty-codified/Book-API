package com.mustycodified.BookApi.services.impl;

import com.mustycodified.BookApi.dtos.requests.RegisterUserDto;
import com.mustycodified.BookApi.dtos.requests.UserLoginDto;
import com.mustycodified.BookApi.dtos.response.UserResponseDto;
import com.mustycodified.BookApi.entities.User;
import com.mustycodified.BookApi.enums.Roles;
import com.mustycodified.BookApi.exceptions.CustomAuthenticationException;
import com.mustycodified.BookApi.exceptions.DuplicateException;
import com.mustycodified.BookApi.exceptions.NotFoundException;
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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
            throw new DuplicateException("User already exists");

        User newUser = User.builder()
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .roles(Roles.USER.getAuthorities().stream().map(Objects::toString
                ).collect(Collectors.joining(" , ")))
                .uuid(appUtil.generateSerialNumber("usr"))
                .walletBalance(BigDecimal.valueOf(5000))
                .build();

        newUser = userRepository.save(newUser);
        return appUtil.getMapper().convertValue(newUser, UserResponseDto.class);

    }

    @Override
    @Transactional
    public UserResponseDto login(UserLoginDto loginDto) {

        try{
            Authentication authentication = authenticationManager
                    .authenticate( new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

            UserResponseDto userResponseDto;
            if (authentication.isAuthenticated()){
             User user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(()-> new BadCredentialsException("Invalid Login details"));

                log.info("Generating access token...");
            String accessToken = jwtUtil.generateToken(customUserDetailsService.loadUserByUsername(user.getEmail()));
                log.info("Access token :" + accessToken);

                userResponseDto = appUtil.getMapper().convertValue(user, UserResponseDto.class);
                appUtil.print(userResponseDto);
                userResponseDto.setToken(accessToken);
            } else {
                throw new BadCredentialsException("Invalid email or password");
            }

            return userResponseDto;

        } catch (Exception ex){
            throw new CustomAuthenticationException(ex.getMessage());

        }
    }

    @Override
    @Transactional
    public UserResponseDto findUser(String userId) {
      User user = userRepository.findByUuid(userId)
                .orElseThrow(()-> new NotFoundException("User not found"));

        return appUtil.getMapper().convertValue(user, UserResponseDto.class);
    }
}
