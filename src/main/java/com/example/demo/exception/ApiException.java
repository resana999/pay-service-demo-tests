package com.example.demo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class ApiException extends ResponseEntityExceptionHandler {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "BAD_REQUEST")
    public HttpEntity<Object> setError(
            HttpMediaTypeNotAcceptableException ex,
            HttpHeaders headers, HttpStatus status,
            WebRequest request) {

        return new HttpEntity<>(get(ex.getMessage(),ex, status.value()));
    }

    private HttpEntity get(String message, Throwable ex, int status) {
        Message m = new Message(message, String.valueOf(status));
        return new HttpEntity<>(m);
    }
}