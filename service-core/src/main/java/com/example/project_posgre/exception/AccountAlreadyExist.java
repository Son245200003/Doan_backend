package com.example.project_posgre.exception;

public class AccountAlreadyExist extends RuntimeException {
    private String message;
    public AccountAlreadyExist(String message){
        super(message);
    }
}
