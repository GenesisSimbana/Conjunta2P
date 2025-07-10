package com.conjunta.simbana.model;

/**
 * Clase que contiene todos los enums del sistema
 */
public class Enums {
    
    /**
     * Enum para el estado del turno
     */
    public enum EstadoTurno {
        ABIERTO,
        CERRADO
    }
    
    /**
     * Enum para el tipo de transacción
     */
    public enum TipoTransaccion {
        INICIO,
        AHORRO,
        DEPOSITO,
        CIERRE
    }
    
    /**
     * Enum para las denominaciones de billetes
     */
    public enum Denominacion {
        UNO("1"),
        CINCO("5"),
        DIEZ("10"),
        VEINTE("20"),
        CINCUENTA("50"),
        CIEN("100");
        
        private final String valor;
        
        Denominacion(String valor) {
            this.valor = valor;
        }
        
        public String getValor() {
            return valor;
        }
        
        public static Denominacion fromValor(String valor) {
            for (Denominacion denominacion : values()) {
                if (denominacion.valor.equals(valor)) {
                    return denominacion;
                }
            }
            throw new IllegalArgumentException("Denominación no válida: " + valor);
        }
    }
} 