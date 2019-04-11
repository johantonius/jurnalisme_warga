package com.project.jw.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsernameAlreadyExistsException extends RuntimeException{
    //generate constructor message to string
    public UsernameAlreadyExistsException(String message){
        super(message);
    }
}
