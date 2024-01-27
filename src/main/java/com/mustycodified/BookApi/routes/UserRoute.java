package com.mustycodified.BookApi.routes;

import com.mustycodified.BookApi.dtos.requests.RegisterUserDto;
import com.mustycodified.BookApi.dtos.response.ApiResponse;
import com.mustycodified.BookApi.dtos.response.UserResponseDto;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Component
public class UserRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        rest("/users")
                .post("/register")
                .description("Register a user")
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .outType(UserResponseDto.class)
                .to("direct:create-user")

                .get("/register/{userId}")
                .description("Retrieve a user")
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .outType(UserResponseDto.class)
                .to("direct:get-user");

        from("direct:create-user")
                .unmarshal().json(JsonLibrary.Jackson, RegisterUserDto.class)
                .log(LoggingLevel.INFO, "Received body {$body}")
                .bean("userServiceImpl", "registerUser(${body})")
                .process(this::extractUriAndAddToResponseHeaders)
                .process(this::extractResponseDtoAndMapToApiResponse)
                .process(exchange -> {
                    exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.CREATED.value());
                })
                .marshal().json()
                .end();

        from("direct:get-user")
                .log(LoggingLevel.INFO, "Received body {$userId}")
                .bean("userServiceImpl", "findUser(${header.userId})")
                .process(this::extractResponseDtoAndMapToApiResponse)
                .process(exchange -> {
                    exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.OK.value());
                })
                .marshal().json()
                .end();

    }

    private void extractUriAndAddToResponseHeaders(Exchange exchange) {
        UserResponseDto responseDto = exchange.getIn().getBody(UserResponseDto.class);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{uuid}")
                .buildAndExpand(responseDto.getUuid())
                .toUri();
        exchange.getMessage().setHeader("location", location);
    }

    public void extractResponseDtoAndMapToApiResponse(Exchange exchange) {
      UserResponseDto responseDto =  exchange.getIn().getBody(UserResponseDto.class);
      ApiResponse<UserResponseDto> apiResponse = new ApiResponse<>();
      apiResponse.setData(responseDto);
      apiResponse.setStatus(true);
      apiResponse.setMessage("Successfully registered");
      exchange.getMessage().setBody(apiResponse);
    }
}
