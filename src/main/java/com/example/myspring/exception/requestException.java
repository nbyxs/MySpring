package com.example.myspring.exception;

import java.rmi.ServerException;

public class requestException extends ServerException {
    public requestException(String message) {
        super(message);
    }
}
