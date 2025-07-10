package com.conjunta.simbana.exception;

/**
 * Excepción lanzada cuando se viola una regla de negocio
 */
public class BusinessException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    public BusinessException(String message) {
        super(message);
    }
    
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public BusinessException(String operation, String reason) {
        super(String.format("No se puede %s: %s", operation, reason));
    }
} 