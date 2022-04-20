package com.ead.authuser.exception;

public class UserExistsInCourseException extends RuntimeException {
    public UserExistsInCourseException(String mensagem) {
        super(mensagem);
    }
}
