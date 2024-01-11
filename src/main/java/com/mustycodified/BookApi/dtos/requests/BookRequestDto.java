package com.mustycodified.BookApi.dtos.requests;


import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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

    @Min(1)
    @Max(10)
    private int quantity;
}
