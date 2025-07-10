package com.conjunta.simbana.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Representa una denominación de billete con su cantidad y monto correspondiente
 */
@Document(collection = "denominaciones_turno")
public class DenominacionTurno {
    
    @Id
    private String id;
    
    @Field("denominacion")
    private BigDecimal denominacion;
    
    @Field("cantidad")
    private Integer cantidad;
    
    @Field("monto")
    private BigDecimal monto;
    
    // Constructor vacío
    public DenominacionTurno() {
    }
    
    // Constructor con parámetros
    public DenominacionTurno(BigDecimal denominacion, Integer cantidad) {
        this.denominacion = denominacion;
        this.cantidad = cantidad;
        this.monto = denominacion.multiply(BigDecimal.valueOf(cantidad));
    }
    
    // Getters y Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public BigDecimal getDenominacion() {
        return denominacion;
    }
    
    public void setDenominacion(BigDecimal denominacion) {
        this.denominacion = denominacion;
        calcularMonto();
    }
    
    public Integer getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
        calcularMonto();
    }
    
    public BigDecimal getMonto() {
        return monto;
    }
    
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
    
    // Método para calcular el monto automáticamente
    private void calcularMonto() {
        if (denominacion != null && cantidad != null) {
            this.monto = denominacion.multiply(BigDecimal.valueOf(cantidad));
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DenominacionTurno that = (DenominacionTurno) obj;
        return Objects.equals(id, that.id) &&
               Objects.equals(denominacion, that.denominacion) &&
               Objects.equals(cantidad, that.cantidad) &&
               Objects.equals(monto, that.monto);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, denominacion, cantidad, monto);
    }
    
    @Override
    public String toString() {
        return "DenominacionTurno{" +
                "id='" + id + '\'' +
                ", denominacion=" + denominacion +
                ", cantidad=" + cantidad +
                ", monto=" + monto +
                '}';
    }
} 