package com.mustycodified.BookApi.routes;

import com.mustycodified.BookApi.dtos.requests.BookRequestDto;
import com.mustycodified.BookApi.dtos.response.BookResponseDto;
import com.mustycodified.BookApi.processors.CreateResponseProcessor;
import lombok.AllArgsConstructor;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
public class BookRoute extends RouteBuilder {

    private final CreateResponseProcessor createResponseProcessor;
    private final CamelContext camelContext;
    @Override
    public void configure() throws Exception {
        camelContext.setStreamCaching(true);
        rest("/books")
                .post("/add")
                .description("Create book REST API")
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .outType(BookResponseDto.class)
                .to("direct:create-book")

                // Needs fixing
                .get()
                .description("Retrieve list of books REST API")
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .outType(BookResponseDto.class)
                .to("direct:get-all-books")

                .get("/{id}")
                .description("Retrieve single book REST API")
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .outType(BookResponseDto.class)
                .to("direct:get-book")

                .delete("delete/{id}")
                .description("Delete book REST API")
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .to("direct:delete-book")

                .put("/edit/{id}")
                .description("Edit book REST API")
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .to("direct:edit-book")

                .post("/borrow/{id}/{userId}")
                .description("Borrow book REST API")
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .to("direct:borrow-book")

                .post("/return/{borrowedBookId}")
                .description("Return book REST API")
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .to("direct:return-book");


        from("direct:create-book")
                .log(LoggingLevel.INFO, "Received body {$body}")
                .unmarshal().json(JsonLibrary.Jackson, BookRequestDto.class)
                .bean("bookServiceImpl", "createBook(${body})")
                .setHeader("operationName", constant("created book"))
                .process(new CreateResponseProcessor<>(BookResponseDto.class))
                .process(exchange -> exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.CREATED.value()))
                .marshal().json()
                .end();

        from("direct:get-book")
                .log(LoggingLevel.INFO, "Received id {${header.id}}")
                .bean("bookServiceImpl", "getBookById(${header.id})")
                .setHeader("operationName", constant("Retrieved single book"))
                .process(new CreateResponseProcessor<>(BookResponseDto.class))
                .process(exchange -> exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.OK.value()))
                .marshal().json()
                .end();

        from("direct:get-all-books")
                .bean("bookServiceImpl", "getAllBooks")
                .setHeader("operationName", constant("Retrieved all books"))
                .process(new CreateResponseProcessor<>(BookResponseDto.class))
                .process(exchange -> exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.OK.value()))
                .marshal().json()
                .end();


        from("direct:delete-book")
                .log(LoggingLevel.INFO, "Received id {${header.id}}")
                .bean("bookServiceImpl", "deleteBook(${header.id})")
                .process(exchange -> exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.NO_CONTENT.value()))
                .end();

        from("direct:edit-book")
                .log(LoggingLevel.INFO, "Received parameters {${header.id}, ${body}}")
                .unmarshal().json(JsonLibrary.Jackson, BookRequestDto.class)
                .bean("bookServiceImpl", "editBook(${header.id}, ${body})")
                .setHeader("operationName", constant("Edited book"))
                .process(new CreateResponseProcessor<>(BookResponseDto.class))
                .process(exchange -> exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.OK.value()))
                .marshal().json()
                .end();


        from("direct:borrow-book")
                .log(LoggingLevel.INFO, "Received parameters {${header.id}, ${header.userId}}")
                .bean("bookServiceImpl", "borrowBook(${header.id}, ${header.userId})")
                .setHeader("operationName", constant("Borrowed a book"))
                .process(new CreateResponseProcessor<>(BookResponseDto.class))
                .process(exchange -> exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.OK.value()))
                .end();

        from("direct:return-book")
                .log(LoggingLevel.INFO, "Received parameters {${header.borrowedBookId}}")
                .bean("bookServiceImpl", "returnBorrowedBook(${header.borrowedBookId})")
                .setHeader("operationName", constant("Returned a book"))
                .process(new CreateResponseProcessor<>(BookResponseDto.class))
                .process(exchange -> exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.OK.value()))
                .end();



    }


}
