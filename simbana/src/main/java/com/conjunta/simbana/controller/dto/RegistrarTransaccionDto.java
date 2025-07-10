package com.conjunta.simbana.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class RegistrarTransaccionDto {
    @NotBlank(message = "El código de caja es obligatorio")
    private String codigoCaja;

    @NotBlank(message = "El código de cajero es obligatorio")
    private String codigoCajero;

    @NotBlank(message = "El código de turno es obligatorio")
    private String codigoTurno;

    @NotBlank(message = "El tipo de transacción es obligatorio")
    private String tipoTransaccion;

    @DecimalMin(value = "0.0", message = "El monto total no puede ser negativo")
    private BigDecimal montoTotal;

    @NotNull(message = "Las denominaciones son obligatorias")
    private List<DenominacionTurnoDto> denominaciones;
} 