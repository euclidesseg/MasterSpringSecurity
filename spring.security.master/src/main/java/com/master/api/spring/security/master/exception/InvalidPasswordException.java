package com.master.api.spring.security.master.exception;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(){
        
    }
    public InvalidPasswordException(String message){
        super(message);
    }
    public InvalidPasswordException(String message, Throwable cause){
        super(message, cause);
    }
}
