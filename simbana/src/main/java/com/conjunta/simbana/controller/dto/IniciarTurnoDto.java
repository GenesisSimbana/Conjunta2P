package com.conjunta.simbana.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class IniciarTurnoDTO {
    @NotBlank(message = "El código del turno es requerido")
    private String codigoTurno;

    @NotBlank(message = "El código de caja es requerido")
    private String codigoCaja;

    @NotBlank(message = "El código de cajero es requerido")
    private String codigoCajero;

    @NotNull(message = "El monto inicial es requerido")
    @DecimalMin(value = "0.01", message = "El monto inicial debe ser mayor a cero")
    @DecimalMax(value = "999999999999.99", message = "El monto inicial excede el límite permitido")
    private BigDecimal montoInicial;
} 