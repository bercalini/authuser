package com.ead.authuser.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String mensagem) {
        super(mensagem);
    }
}
