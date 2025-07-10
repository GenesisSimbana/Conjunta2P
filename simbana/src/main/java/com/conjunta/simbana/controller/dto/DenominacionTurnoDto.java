package com.conjunta.simbana.controller.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * DTO para DenominacionTurno
 */
public class DenominacionTurnoDto {
    
    @NotNull(message = "La denominación es obligatoria")
    @DecimalMin(value = "0.01", message = "La denominación debe ser mayor a 0")
    private BigDecimal denominacion;
    
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 0, message = "La cantidad debe ser mayor o igual a 0")
    private Integer cantidad;
    
    // Constructor vacío
    public DenominacionTurnoDto() {
    }
    
    // Constructor con parámetros
    public DenominacionTurnoDto(BigDecimal denominacion, Integer cantidad) {
        this.denominacion = denominacion;
        this.cantidad = cantidad;
    }
    
    // Getters y Setters
    public BigDecimal getDenominacion() {
        return denominacion;
    }
    
    public void setDenominacion(BigDecimal denominacion) {
        this.denominacion = denominacion;
    }
    
    public Integer getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DenominacionTurnoDto that = (DenominacionTurnoDto) obj;
        return Objects.equals(denominacion, that.denominacion) &&
               Objects.equals(cantidad, that.cantidad);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(denominacion, cantidad);
    }
    
    @Override
    public String toString() {
        return "DenominacionTurnoDto{" +
                "denominacion=" + denominacion +
                ", cantidad=" + cantidad +
                '}';
    }
} 