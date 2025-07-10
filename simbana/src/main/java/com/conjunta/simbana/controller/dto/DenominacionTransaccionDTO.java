package com.conjunta.simbana.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class DenominacionTransaccionDTO {
    @Schema(description = "ID único de la denominación")
    private String id;

    @Schema(description = "ID de la transacción")
    private String transaccionId;

    @Min(value = 1, message = "El valor del billete debe ser mayor a 0")
    private int billete;

    @Min(value = 0, message = "La cantidad no puede ser negativa")
    private int cantidad;

    @DecimalMin(value = "0.0", message = "El monto no puede ser negativo")
    private BigDecimal monto;

    @Schema(description = "Versión del documento")
    private Long version;
} 