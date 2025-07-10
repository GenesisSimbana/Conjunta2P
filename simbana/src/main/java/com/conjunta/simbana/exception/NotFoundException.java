package com.conjunta.simbana.exception;

public class NotFoundException extends RuntimeException {

    private final Integer errorCode;

    public NotFoundException(String message) {
        super(message);
        this.errorCode = 404;
    }

    public NotFoundException(String message, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return "Error code: " + this.errorCode + ", message: " + super.getMessage();
    }

    public Integer getErrorCode() {
        return errorCode;
    }
} 