package com.mustycodified.BookApi.processors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mustycodified.BookApi.dtos.response.ApiResponse;
import com.mustycodified.BookApi.dtos.response.BookResponseDto;
import com.mustycodified.BookApi.dtos.response.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
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


//                Objects.requireNonNull(apiResponse.getBody()).getData().stream()
//                        .map(book -> appUtil.getMapper().convertValue(book, BookResponseDto.class))
//                        .collect(Collectors.toList())

           String operationName =  exchange.getIn().getHeader("operationName", String.class);

             ApiResponse<T> apiResponse = new ApiResponse<>();
             apiResponse.setData(responseData);
             apiResponse.setStatus(true);

             apiResponse.setMessage("Successfully " + (operationName != null ? operationName : "completed"));
             exchange.getMessage().setBody(apiResponse);
         }



}

