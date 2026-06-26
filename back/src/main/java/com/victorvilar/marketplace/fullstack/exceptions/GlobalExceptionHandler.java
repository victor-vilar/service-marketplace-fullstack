package com.victorvilar.marketplace.fullstack.exceptions;

import com.victorvilar.marketplace.fullstack.domain.ApiResponse;
import com.victorvilar.marketplace.fullstack.domain.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleUserNotFoundException(UserNotFoundException ex){
        ErrorResponse err = new ErrorResponse(HttpStatus.NOT_FOUND.value(),ex.getMessage());
        return ResponseEntity.status(err.getHttpStatus()).body(ApiResponse.fail(err));
    }

    @ExceptionHandler(SameCustomerAndProviderException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleSameCustomerAndProviderExeption(SameCustomerAndProviderException ex){
        ErrorResponse err = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.status(err.getHttpStatus()).body(ApiResponse.fail(err));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleOthersExceptions(MethodArgumentNotValidException ex){
        String msg = "O objeto não pode ser construído !";
        ErrorResponse err = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
        return ResponseEntity.status(err.getHttpStatus()).body(ApiResponse.fail(err));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleBadCredentialsException(BadCredentialsException ex){
        String msg = "Usuário ou Senha inválidos !";
        ErrorResponse err = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), msg);
        return ResponseEntity.status(err.getHttpStatus()).body(ApiResponse.fail(err));
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleBadCredentialsException(DisabledException ex){
        String msg = "Conta inátiva !";
        ErrorResponse err = new ErrorResponse(HttpStatus.FORBIDDEN.value(), msg);
        return ResponseEntity.status(err.getHttpStatus()).body(ApiResponse.fail(err));
    }
}
