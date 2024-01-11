package com.mustycodified.BookApi.dtos.requests;


import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter

public class BookRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotEmpty
    private int quantity;
}
