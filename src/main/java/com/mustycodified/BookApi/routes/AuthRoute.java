package com.mustycodified.BookApi.routes;

import com.mustycodified.BookApi.dtos.requests.UserLoginDto;
import com.mustycodified.BookApi.dtos.response.UserResponseDto;
import com.mustycodified.BookApi.processors.CreateResponseProcessor;
import lombok.RequiredArgsConstructor;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthRoute extends RouteBuilder {
    private final UserRoute userRoute;
    private final CamelContext camelContext;
    @Override
    public void configure() throws Exception {
        camelContext.setStreamCaching(true);

        rest("/auth")
                .post("/login")
                .description("Login a user")
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .outType(UserResponseDto.class)
                .to("direct:login-user");

        from("direct:login-user")
                .log(LoggingLevel.INFO, "Received body (${body})")
                .unmarshal().json(JsonLibrary.Jackson, UserLoginDto.class)
                .bean("userServiceImpl", "login(${body})")
                .process(new CreateResponseProcessor<>(UserResponseDto.class))
                .process(exchange -> {
                    exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.OK.value());
                })
                .marshal().json(JsonLibrary.Jackson);


    }
}
