package com.conjunta.simbana.controller.dto;

import com.conjunta.simbana.model.Enums;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "DTO para Turno de Caja")
public class TurnoCajaDTO {
    
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

    @Schema(description = "Fecha y hora de inicio del turno")
    private LocalDateTime inicioTurno;

    @Schema(description = "Monto inicial del turno", example = "1000.00")
    @NotNull(message = "El monto inicial es requerido")
    @DecimalMin(value = "0.01", message = "El monto inicial debe ser mayor a cero")
    @DecimalMax(value = "999999999999.99", message = "El monto inicial excede el límite permitido")
    private BigDecimal montoInicial;

    @Schema(description = "Fecha y hora de fin del turno")
    private LocalDateTime finTurno;

    @Schema(description = "Monto final del turno", example = "1500.00")
    @DecimalMin(value = "0.00", message = "El monto final no puede ser negativo")
    @DecimalMax(value = "999999999999.99", message = "El monto final excede el límite permitido")
    private BigDecimal montoFinal;

    @Schema(description = "Estado del turno", example = "ABIERTO")
    private Enums.EstadoTurno estado;

    @Schema(description = "Versión del registro para control de concurrencia")
    private Long version;

    // Constructor vacío
    public TurnoCajaDTO() {
    }

    // Constructor con parámetros
    public TurnoCajaDTO(String codigoTurno, String codigoCaja, String codigoCajero, BigDecimal montoInicial) {
        this.codigoTurno = codigoTurno;
        this.codigoCaja = codigoCaja;
        this.codigoCajero = codigoCajero;
        this.montoInicial = montoInicial;
        this.estado = Enums.EstadoTurno.ABIERTO;
    }

    // Getters y Setters
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

    public LocalDateTime getInicioTurno() {
        return inicioTurno;
    }

    public void setInicioTurno(LocalDateTime inicioTurno) {
        this.inicioTurno = inicioTurno;
    }

    public BigDecimal getMontoInicial() {
        return montoInicial;
    }

    public void setMontoInicial(BigDecimal montoInicial) {
        this.montoInicial = montoInicial;
    }

    public LocalDateTime getFinTurno() {
        return finTurno;
    }

    public void setFinTurno(LocalDateTime finTurno) {
        this.finTurno = finTurno;
    }

    public BigDecimal getMontoFinal() {
        return montoFinal;
    }

    public void setMontoFinal(BigDecimal montoFinal) {
        this.montoFinal = montoFinal;
    }

    public Enums.EstadoTurno getEstado() {
        return estado;
    }

    public void setEstado(Enums.EstadoTurno estado) {
        this.estado = estado;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
} 