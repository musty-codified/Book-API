//package com.mustycodified.BookApi.utils;
//import com.mustycodified.BookApi.entities.UserEntity;
//import com.mustycodified.BookApi.enums.Roles;
//import com.mustycodified.BookApi.repositories.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
//@RequiredArgsConstructor
//@Component
//public class InitialUserSetup implements CommandLineRunner {
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final AppUtils appUtil;
//
//    @Override
//    public void run(String... args) throws Exception {
//        System.out.println("Starting initial user setup");
//        UserEntity initUser = UserEntity.builder()
//                .email("test@gmail.com")
//                .firstName("Papi")
//                .lastName("Marciano")
//                .lastLoginDate(new Date())
//                .roles(Roles.AUTHOR.getAuthorities().stream().map(Objects::toString)
//                        .collect(Collectors.joining(" , ")))
//                .uuid(appUtil.generateSerialNumber("auth"))
//                .password(passwordEncoder.encode("1234"))
//                .build();
//        userRepository.save(initUser);
//
//    }
//}
