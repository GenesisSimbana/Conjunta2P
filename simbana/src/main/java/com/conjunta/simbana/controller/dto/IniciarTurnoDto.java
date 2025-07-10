package com.conjunta.simbana.controller.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * DTO para iniciar un turno de caja
 */
public class IniciarTurnoDto {
    
    @NotBlank(message = "El código de caja es obligatorio")
    private String codigoCaja;
    
    @NotBlank(message = "El código de cajero es obligatorio")
    private String codigoCajero;
    
    @NotBlank(message = "La fecha es obligatoria")
    private String fecha; // formato yyyyMMdd
    
    @NotNull(message = "El monto inicial es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto inicial debe ser mayor a 0")
    private BigDecimal montoInicial;
    
    private List<DenominacionTurnoDto> denominacionesIniciales;
    
    // Constructor vacío
    public IniciarTurnoDto() {
    }
    
    // Constructor con parámetros
    public IniciarTurnoDto(String codigoCaja, String codigoCajero, String fecha, BigDecimal montoInicial) {
        this.codigoCaja = codigoCaja;
        this.codigoCajero = codigoCajero;
        this.fecha = fecha;
        this.montoInicial = montoInicial;
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
    
    public BigDecimal getMontoInicial() {
        return montoInicial;
    }
    
    public void setMontoInicial(BigDecimal montoInicial) {
        this.montoInicial = montoInicial;
    }
    
    public List<DenominacionTurnoDto> getDenominacionesIniciales() {
        return denominacionesIniciales;
    }
    
    public void setDenominacionesIniciales(List<DenominacionTurnoDto> denominacionesIniciales) {
        this.denominacionesIniciales = denominacionesIniciales;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        IniciarTurnoDto that = (IniciarTurnoDto) obj;
        return Objects.equals(codigoCaja, that.codigoCaja) &&
               Objects.equals(codigoCajero, that.codigoCajero) &&
               Objects.equals(fecha, that.fecha) &&
               Objects.equals(montoInicial, that.montoInicial) &&
               Objects.equals(denominacionesIniciales, that.denominacionesIniciales);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(codigoCaja, codigoCajero, fecha, montoInicial, denominacionesIniciales);
    }
    
    @Override
    public String toString() {
        return "IniciarTurnoDto{" +
                "codigoCaja='" + codigoCaja + '\'' +
                ", codigoCajero='" + codigoCajero + '\'' +
                ", fecha='" + fecha + '\'' +
                ", montoInicial=" + montoInicial +
                ", denominacionesIniciales=" + denominacionesIniciales +
                '}';
    }
} 