package com.polovyi.ivan.tutorials.exception;

public class UnprocessableEntityException extends RuntimeException {

    public UnprocessableEntityException(String errorMessage) {
        super(errorMessage);
    }
}