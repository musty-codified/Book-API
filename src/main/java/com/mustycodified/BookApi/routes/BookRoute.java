package com.mustycodified.BookApi.routes;

import com.mustycodified.BookApi.dtos.requests.BookRequestDto;
import com.mustycodified.BookApi.dtos.response.BookResponseDto;
import com.mustycodified.BookApi.dtos.response.UserResponseDto;
import com.mustycodified.BookApi.processors.CreateResponseProcessor;
import lombok.AllArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BookRoute extends RouteBuilder {

    private final CreateResponseProcessor createResponseProcessor;
    @Override
    public void configure() throws Exception {
        rest("/books")
                .post("/add")
                .description("Create book REST API")
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .outType(BookResponseDto.class)
                .to("direct:create-book");

        from("direct:create-book")
                .log(LoggingLevel.INFO, "Received body {$body}")
                .unmarshal().json(JsonLibrary.Jackson, BookRequestDto.class)
                .bean("bookServiceImpl", "createBook(${body})")
                .setHeader("operation", constant("created book"))
                .process(new CreateResponseProcessor<>(BookResponseDto.class))
//                .setHeader("responseType", constant(UserResponseDto.class)) // Specify the response data type
                .process(exchange -> exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.CREATED.value()))
                .marshal().json()
                .end();

    }


}
