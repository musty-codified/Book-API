package com.mustycodified.BookApi.dtos.requests;


import lombok.*;

import javax.validation.constraints.*;
import java.math.BigDecimal;

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

    @NotNull
    private BigDecimal price;
}
