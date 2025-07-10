package com.conjunta.simbana.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Clave compuesta para la entidad TurnoCaja
 * Conformada por código de caja, código de cajero y fecha en formato yyyyMMdd
 */
public class TurnoCajaId implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String codigoCaja;
    private String codigoCajero;
    private String fecha; // formato yyyyMMdd
    
    // Constructor vacío
    public TurnoCajaId() {
    }
    
    // Constructor con parámetros
    public TurnoCajaId(String codigoCaja, String codigoCajero, String fecha) {
        this.codigoCaja = codigoCaja;
        this.codigoCajero = codigoCajero;
        this.fecha = fecha;
    }
    
    // Getters y Setters
    public String getCodigoCaja() {
        return codigoCaja;
    }
    
    public void setCodigoCaja(String codigoCaja) {
        this.codigoCaja = codigoCaja;
    }
    
    public String getCodigoCajero() {
        return codigoCajero;
    }
    
    public void setCodigoCajero(String codigoCajero) {
        this.codigoCajero = codigoCajero;
    }
    
    public String getFecha() {
        return fecha;
    }
    
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TurnoCajaId that = (TurnoCajaId) obj;
        return Objects.equals(codigoCaja, that.codigoCaja) &&
               Objects.equals(codigoCajero, that.codigoCajero) &&
               Objects.equals(fecha, that.fecha);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(codigoCaja, codigoCajero, fecha);
    }
    
    @Override
    public String toString() {
        return "TurnoCajaId{" +
                "codigoCaja='" + codigoCaja + '\'' +
                ", codigoCajero='" + codigoCajero + '\'' +
                ", fecha='" + fecha + '\'' +
                '}';
    }
} 