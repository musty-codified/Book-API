package com.mustycodified.BookApi.exceptions;


import com.mustycodified.BookApi.dtos.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponse<String> handleValidationException(ValidationException ex){
        LOGGER.error(ex.getMessage());
        return new ApiResponse<>("Error: "+ex.getMessage(), false,null);
    }
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiResponse<String> handleNotFoundException(NotFoundException ex){
        LOGGER.error(ex.getMessage());
        return new ApiResponse<>("Error: "+ex.getMessage(), false,null);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ApiResponse<String> handleAuthenticationException(AuthenticationException ex){
        LOGGER.error(ex.getMessage());
        return new ApiResponse<>("Error: "+ex.getMessage(), false,null);
    }

    @ExceptionHandler(UnavailableException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiResponse<String> handleUnavailableException(UnavailableException ex){
        LOGGER.error(ex.getMessage());
        return new ApiResponse<>("Error: "+ex.getMessage(), false,null);
    }
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiResponse<String> handleOtherServiceException(Exception ex){
        LOGGER.error(ex.getMessage());
        return new ApiResponse<>("Error: "+ex.getMessage(), false,null);
    }
}
