package com.example.project_posgre.exception;

public class InvalidParamException extends RuntimeException {
    private String message;
    public InvalidParamException(String message){
        super(message);
    }
}