package com.exchangerateprovider.exception;

public class ApiConnectionException extends RuntimeException{
    public ApiConnectionException(String message){
        super(message);
    }
}
