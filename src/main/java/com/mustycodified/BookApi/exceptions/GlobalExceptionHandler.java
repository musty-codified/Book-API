package com.mustycodified.BookApi.exceptions;

import com.mustycodified.BookApi.dtos.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
         Map<String, String> errors = new HashMap<>();
         ex.getBindingResult().getAllErrors().forEach((error)->{
         String fieldName =  ((FieldError)error).getField();
         String message = error.getDefaultMessage();
         errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST) ;
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleValidationException(ValidationException ex){
        LOGGER.error(ex.getMessage());
        return new ResponseEntity<>(new ApiResponse<>("Error: " + ex.getMessage(), false,null), new HttpHeaders(), HttpStatus.BAD_REQUEST) ;
    }

    @ExceptionHandler(DuplicateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleDuplicateException(DuplicateException ex){
        LOGGER.error(ex.getMessage());
        return new ResponseEntity<>(new ApiResponse<>("Error: " + ex.getMessage(), false,null), new HttpHeaders(), HttpStatus.CONFLICT) ;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ResponseBody
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex){
        LOGGER.error(ex.getMessage());
        return new ResponseEntity<>(new ApiResponse<>("Error: " + ex.getMessage(), false, null ), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CustomAuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ApiResponse<String> handleCustomAuthenticationException(CustomAuthenticationException ex){
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
