package com.conjunta.simbana.exception;

/**
 * Excepción lanzada cuando no se encuentra un recurso solicitado
 */
public class NotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    public NotFoundException(String message) {
        super(message);
    }
    
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public NotFoundException(String resource, String identifier) {
        super(String.format("%s no encontrado con identificador: %s", resource, identifier));
    }
} 