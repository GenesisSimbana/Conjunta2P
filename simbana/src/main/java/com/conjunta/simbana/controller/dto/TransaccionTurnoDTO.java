package com.conjunta.simbana.controller.dto;

import com.conjunta.simbana.model.Enums;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "DTO para Transacción de Turno")
public class TransaccionTurnoDTO {
    
    @Schema(description = "ID único de la transacción")
    private Integer id;

    @Schema(description = "Código del turno", example = "TURNO-2024-001")
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

    @Schema(description = "Tipo de transacción", example = "DEPOSITO")
    @NotNull(message = "El tipo de transacción es requerido")
    private Enums.TipoTransaccion tipoTransaccion;

    @Schema(description = "Monto total de la transacción", example = "500.00")
    @NotNull(message = "El monto total es requerido")
    @DecimalMin(value = "0.01", message = "El monto total debe ser mayor a cero")
    @DecimalMax(value = "999999999999.99", message = "El monto total excede el límite permitido")
    private BigDecimal montoTotal;

    @Schema(description = "Fecha y hora de la transacción")
    private LocalDateTime fechaHora;

    @Schema(description = "Versión del registro para control de concurrencia")
    private Long version;

    @Schema(description = "Lista de denominaciones de la transacción")
    private List<DenominacionTransaccionDTO> denominaciones;

    // Constructor vacío
    public TransaccionTurnoDTO() {
    }

    // Constructor con parámetros
    public TransaccionTurnoDTO(String codigoTurno, String codigoCaja, String codigoCajero, 
                              Enums.TipoTransaccion tipoTransaccion, BigDecimal montoTotal) {
        this.codigoTurno = codigoTurno;
        this.codigoCaja = codigoCaja;
        this.codigoCajero = codigoCajero;
        this.tipoTransaccion = tipoTransaccion;
        this.montoTotal = montoTotal;
        this.fechaHora = LocalDateTime.now();
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Enums.TipoTransaccion getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(Enums.TipoTransaccion tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public List<DenominacionTransaccionDTO> getDenominaciones() {
        return denominaciones;
    }

    public void setDenominaciones(List<DenominacionTransaccionDTO> denominaciones) {
        this.denominaciones = denominaciones;
    }
} 