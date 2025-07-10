package com.conjunta.simbana.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class TransaccionTurnoDTO {
    @Schema(description = "ID único de la transacción")
    private String id;

    @NotBlank(message = "El código de turno es requerido")
    private String codigoTurno;

    @NotBlank(message = "El código de caja es requerido")
    private String codigoCaja;

    @NotBlank(message = "El código de cajero es requerido")
    private String codigoCajero;

    @NotBlank(message = "El tipo de transacción es requerido")
    private String tipoTransaccion;

    @DecimalMin(value = "0.0", message = "El monto total no puede ser negativo")
    private BigDecimal montoTotal;

    @Schema(description = "Fecha y hora de la transacción")
    private LocalDateTime fecha;

    @NotNull(message = "Las denominaciones son requeridas")
    private List<DenominacionDTO> denominaciones;

    @Schema(description = "Versión del documento")
    private Long version;

    @Data
    @NoArgsConstructor
    public static class DenominacionDTO {
        @Min(value = 1, message = "El valor del billete debe ser mayor a 0")
        private int billete;

        @Min(value = 0, message = "La cantidad no puede ser negativa")
        private int cantidad;

        @DecimalMin(value = "0.0", message = "El monto no puede ser negativo")
        private BigDecimal monto;
    }
} 