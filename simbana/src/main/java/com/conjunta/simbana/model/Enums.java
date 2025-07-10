package com.conjunta.simbana.model;

public class Enums {
    
    public enum EstadoTurno {
        ABIERTO,
        CERRADO
    }
    
    public enum TipoTransaccion {
        INICIO,
        AHORRO,
        DEPOSITO,
        CIERRE
    }
            
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