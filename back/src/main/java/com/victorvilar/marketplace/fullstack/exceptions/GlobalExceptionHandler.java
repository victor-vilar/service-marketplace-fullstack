package com.victorvilar.marketplace.fullstack.exceptions;

import com.victorvilar.marketplace.fullstack.domain.ApiResponse;
import com.victorvilar.marketplace.fullstack.domain.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleUserNotFoundException(UserNotFoundException ex){
        ErrorResponse erro = new ErrorResponse(HttpStatus.NOT_FOUND.value(),ex.getMessage());
        return ResponseEntity.status(erro.getHttpStatus()).body(ApiResponse.fail().build(erro));
    }
}
