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

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    @Min(1)
    @Max(10)
    private int quantity;

    @NotNull(message = "Price is required")
    private BigDecimal price;
}
