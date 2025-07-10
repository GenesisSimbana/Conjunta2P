package com.conjunta.simbana.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "DTO para cerrar un turno de caja")
public class CerrarTurnoDTO {
    
    @Schema(description = "Monto final del turno", example = "1500.00")
    @NotNull(message = "El monto final es requerido")
    @DecimalMin(value = "0.00", message = "El monto final no puede ser negativo")
    @DecimalMax(value = "999999999999.99", message = "El monto final excede el límite permitido")
    private BigDecimal montoFinal;

    // Constructor vacío
    public CerrarTurnoDTO() {
    }

    // Constructor con parámetros
    public CerrarTurnoDTO(BigDecimal montoFinal) {
        this.montoFinal = montoFinal;
    }

    // Getters y Setters
    public BigDecimal getMontoFinal() {
        return montoFinal;
    }

    public void setMontoFinal(BigDecimal montoFinal) {
        this.montoFinal = montoFinal;
    }
} 