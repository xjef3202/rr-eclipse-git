package com.sysco.exception;


public class ServiceException extends RuntimeException {

    private String message;

    public ServiceException(String message) {
        super(message);
    }

}
