package com.conjunta.simbana.controller.dto;

import com.conjunta.simbana.model.TransaccionTurno.TipoTransaccion;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * DTO para registrar una transacción de turno
 */
public class RegistrarTransaccionDto {
    
    @NotBlank(message = "El código de caja es obligatorio")
    private String codigoCaja;
    
    @NotBlank(message = "El código de cajero es obligatorio")
    private String codigoCajero;
    
    @NotBlank(message = "El código de turno es obligatorio")
    private String codigoTurno;
    
    @NotNull(message = "El tipo de transacción es obligatorio")
    private TipoTransaccion tipoTransaccion;
    
    @NotNull(message = "El monto total es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto total debe ser mayor a 0")
    private BigDecimal montoTotal;
    
    @NotNull(message = "Las denominaciones son obligatorias")
    private List<DenominacionTurnoDto> denominaciones;
    
    // Constructor vacío
    public RegistrarTransaccionDto() {
    }
    
    // Constructor con parámetros
    public RegistrarTransaccionDto(String codigoCaja, String codigoCajero, String codigoTurno, 
                                  TipoTransaccion tipoTransaccion, BigDecimal montoTotal) {
        this.codigoCaja = codigoCaja;
        this.codigoCajero = codigoCajero;
        this.codigoTurno = codigoTurno;
        this.tipoTransaccion = tipoTransaccion;
        this.montoTotal = montoTotal;
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
    
    public TipoTransaccion getTipoTransaccion() {
        return tipoTransaccion;
    }
    
    public void setTipoTransaccion(TipoTransaccion tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }
    
    public BigDecimal getMontoTotal() {
        return montoTotal;
    }
    
    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }
    
    public List<DenominacionTurnoDto> getDenominaciones() {
        return denominaciones;
    }
    
    public void setDenominaciones(List<DenominacionTurnoDto> denominaciones) {
        this.denominaciones = denominaciones;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RegistrarTransaccionDto that = (RegistrarTransaccionDto) obj;
        return Objects.equals(codigoCaja, that.codigoCaja) &&
               Objects.equals(codigoCajero, that.codigoCajero) &&
               Objects.equals(codigoTurno, that.codigoTurno) &&
               tipoTransaccion == that.tipoTransaccion &&
               Objects.equals(montoTotal, that.montoTotal) &&
               Objects.equals(denominaciones, that.denominaciones);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(codigoCaja, codigoCajero, codigoTurno, tipoTransaccion, montoTotal, denominaciones);
    }
    
    @Override
    public String toString() {
        return "RegistrarTransaccionDto{" +
                "codigoCaja='" + codigoCaja + '\'' +
                ", codigoCajero='" + codigoCajero + '\'' +
                ", codigoTurno='" + codigoTurno + '\'' +
                ", tipoTransaccion=" + tipoTransaccion +
                ", montoTotal=" + montoTotal +
                ", denominaciones=" + denominaciones +
                '}';
    }
} 