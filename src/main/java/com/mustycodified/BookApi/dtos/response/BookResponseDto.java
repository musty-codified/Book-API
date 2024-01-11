package com.mustycodified.BookApi.dtos.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookResponseDto {
    private Long id;
    private String title;
    private String author;
    private int quantity;
}
