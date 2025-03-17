package com.example.test.exception;

public class NoDataFoundException extends RuntimeException{
    public NoDataFoundException(String message) {
        super(message);
    }
}
