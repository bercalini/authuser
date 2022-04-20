package com.ead.authuser.exceptionHandler;

import com.ead.authuser.exception.UserExistsInCourseException;
import com.ead.authuser.exception.UserNotFoundException;
import com.ead.authuser.exceptionHandler.problema.Problema;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<Object> handleUserNotFoundException(Exception ex, WebRequest request) {
        Problema problema = Problema.builder().mensagem(ex.getMessage()).data(LocalDateTime.now()).status(HttpStatus.NOT_FOUND.value()).build();
        return handleExceptionInternal(ex, problema, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(UserExistsInCourseException.class)
    protected ResponseEntity<Object> handleUserExistsInCourseException(Exception ex, WebRequest request) {
        Problema problema = Problema.builder().mensagem(ex.getMessage()).data(LocalDateTime.now()).status(HttpStatus.CONFLICT.value()).build();
        return handleExceptionInternal(ex, problema, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }
}
