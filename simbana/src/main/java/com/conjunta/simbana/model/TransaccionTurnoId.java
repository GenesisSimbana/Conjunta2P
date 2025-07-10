package com.conjunta.simbana.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Clave compuesta para la entidad TransaccionTurno
 * Conformada por códigos de caja, cajero, turno y código de transacción (UUID)
 */
public class TransaccionTurnoId implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String codigoCaja;
    private String codigoCajero;
    private String codigoTurno;
    private String codigoTransaccion; // UUID
    
    // Constructor vacío
    public TransaccionTurnoId() {
    }
    
    // Constructor con parámetros
    public TransaccionTurnoId(String codigoCaja, String codigoCajero, String codigoTurno, String codigoTransaccion) {
        this.codigoCaja = codigoCaja;
        this.codigoCajero = codigoCajero;
        this.codigoTurno = codigoTurno;
        this.codigoTransaccion = codigoTransaccion;
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
    
    public String getCodigoTurno() {
        return codigoTurno;
    }
    
    public void setCodigoTurno(String codigoTurno) {
        this.codigoTurno = codigoTurno;
    }
    
    public String getCodigoTransaccion() {
        return codigoTransaccion;
    }
    
    public void setCodigoTransaccion(String codigoTransaccion) {
        this.codigoTransaccion = codigoTransaccion;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TransaccionTurnoId that = (TransaccionTurnoId) obj;
        return Objects.equals(codigoCaja, that.codigoCaja) &&
               Objects.equals(codigoCajero, that.codigoCajero) &&
               Objects.equals(codigoTurno, that.codigoTurno) &&
               Objects.equals(codigoTransaccion, that.codigoTransaccion);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(codigoCaja, codigoCajero, codigoTurno, codigoTransaccion);
    }
    
    @Override
    public String toString() {
        return "TransaccionTurnoId{" +
                "codigoCaja='" + codigoCaja + '\'' +
                ", codigoCajero='" + codigoCajero + '\'' +
                ", codigoTurno='" + codigoTurno + '\'' +
                ", codigoTransaccion='" + codigoTransaccion + '\'' +
                '}';
    }
} 