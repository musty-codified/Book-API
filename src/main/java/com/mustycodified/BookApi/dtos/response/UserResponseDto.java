package com.mustycodified.BookApi.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponseDto {

    private String uuid;
    private String firstName;

    private String lastName;

    private String email;

    private String token;

    private List<BookResponseDto> bookResponseDtos;
}
