package com.ming.shopdemo.auth.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@Slf4j
@RestControllerAdvice
public class AuthExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UnAuthorizedException.class)
    public ProblemDetail handleUnAuthorizedException(UnAuthorizedException e) {
        log.warn("UnAuthorizedException: ", e);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNAUTHORIZED,
                e.getMessage()
        );
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
}
