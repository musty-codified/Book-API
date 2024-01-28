package com.mustycodified.BookApi.processors;

import com.mustycodified.BookApi.dtos.response.ApiResponse;
import com.mustycodified.BookApi.dtos.response.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateResponseProcessor<T> implements Processor {
    private Class<T> data;

    @Override
    public void process(Exchange exchange) throws Exception {
        T responseData =  exchange.getIn().getBody(data);

         String operation =  exchange.getIn().getHeader("operation", String.class);

             ApiResponse<T> apiResponse = new ApiResponse<>();
             apiResponse.setData(responseData);
             apiResponse.setStatus(true);

             apiResponse.setMessage("Successfully " + (operation != null ? operation : "completed"));
             exchange.getMessage().setBody(apiResponse);
         }
    }

