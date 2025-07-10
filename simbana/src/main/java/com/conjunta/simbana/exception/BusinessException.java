package com.conjunta.simbana.exception;

public class BusinessException extends RuntimeException {

    private final Integer errorCode;

    public BusinessException(String message) {
        super(message);
        this.errorCode = 400;
    }

    public BusinessException(String message, Integer errorCode) {
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