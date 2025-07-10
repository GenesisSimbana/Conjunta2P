package com.conjunta.simbana.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CerrarTurnoDTO {
    @Schema(description = "Monto final del turno")
    @NotNull(message = "El monto final es requerido")
    @DecimalMin(value = "0.00", message = "El monto final no puede ser negativo")
    @DecimalMax(value = "999999999999.99", message = "El monto final excede el límite permitido")
    private BigDecimal montoFinal;
} 