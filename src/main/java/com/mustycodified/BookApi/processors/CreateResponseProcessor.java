package com.mustycodified.BookApi.processors;

import com.mustycodified.BookApi.dtos.response.ApiResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateResponseProcessor<T> implements Processor {
    private Class<T> data;
    private List<T> dataList;

    public CreateResponseProcessor(Class<T> data) {
        this.data = data;
    }
    public CreateResponseProcessor(List<T> dataList) {
        this.dataList = dataList;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        T responseData =  exchange.getIn().getBody(data);
        String operationName = exchange.getIn().getHeader("operationName", String.class);

        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setData(responseData);
        apiResponse.setStatus(true);

        apiResponse.setMessage("Successfully " + (operationName != null ? operationName : "completed"));
        exchange.getMessage().setBody(apiResponse);

         }

}

