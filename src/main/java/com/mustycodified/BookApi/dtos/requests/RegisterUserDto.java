package com.mustycodified.BookApi.dtos.requests;


import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterUserDto {

    @NotBlank(message = "First name cannot be empty")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty")
    private String lastName;

    @Email
    @NotBlank(message = "Invalid email address")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max=25, message="Password must be equal to or greater than 8 character and less than 30 characters")
    private String password;
}
