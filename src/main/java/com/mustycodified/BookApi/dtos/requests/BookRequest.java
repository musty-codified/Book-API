package com.mustycodified.BookApi.dtos.requests;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter

public class BookRequest {

    private String title;
    private String author;
}
