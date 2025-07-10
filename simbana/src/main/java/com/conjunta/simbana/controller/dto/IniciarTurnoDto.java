package com.conjunta.simbana.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "DTO para iniciar un turno de caja")
public class IniciarTurnoDTO {
    
    @Schema(description = "Código único del turno", example = "TURNO-2024-001")
    @NotBlank(message = "El código del turno es requerido")
    @Size(min = 1, max = 30, message = "El código del turno debe tener entre 1 y 30 caracteres")
    private String codigoTurno;

    @Schema(description = "Código de la caja", example = "CAJA001")
    @NotBlank(message = "El código de caja es requerido")
    @Size(min = 1, max = 10, message = "El código de caja debe tener entre 1 y 10 caracteres")
    private String codigoCaja;

    @Schema(description = "Código del cajero", example = "CAJ001")
    @NotBlank(message = "El código de cajero es requerido")
    @Size(min = 1, max = 10, message = "El código de cajero debe tener entre 1 y 10 caracteres")
    private String codigoCajero;

    @Schema(description = "Monto inicial del turno", example = "1000.00")
    @NotNull(message = "El monto inicial es requerido")
    @DecimalMin(value = "0.01", message = "El monto inicial debe ser mayor a cero")
    @DecimalMax(value = "999999999999.99", message = "El monto inicial excede el límite permitido")
    private BigDecimal montoInicial;

    // Constructor vacío
    public IniciarTurnoDTO() {
    }

    // Constructor con parámetros
    public IniciarTurnoDTO(String codigoTurno, String codigoCaja, String codigoCajero, BigDecimal montoInicial) {
        this.codigoTurno = codigoTurno;
        this.codigoCaja = codigoCaja;
        this.codigoCajero = codigoCajero;
        this.montoInicial = montoInicial;
    }

    public String getCodigoTurno() {
        return codigoTurno;
    }

    public void setCodigoTurno(String codigoTurno) {
        this.codigoTurno = codigoTurno;
    }

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

    public BigDecimal getMontoInicial() {
        return montoInicial;
    }

    public void setMontoInicial(BigDecimal montoInicial) {
        this.montoInicial = montoInicial;
    }

    @Override
    public String toString() {
        return "IniciarTurnoDTO{" +
                "codigoTurno='" + codigoTurno + '\'' +
                ", codigoCaja='" + codigoCaja + '\'' +
                ", codigoCajero='" + codigoCajero + '\'' +
                ", montoInicial=" + montoInicial +
                '}';
    }
} 