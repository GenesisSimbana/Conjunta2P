package com.conjunta.simbana.controller.dto;

import com.conjunta.simbana.model.Enums;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "DTO para Denominación de Transacción")
public class DenominacionTransaccionDTO {
    
    @Schema(description = "ID único de la denominación")
    private Integer id;

    @Schema(description = "ID de la transacción")
    private Integer transaccionId;

    @Schema(description = "Tipo de billete", example = "CIEN")
    @NotNull(message = "El tipo de billete es requerido")
    private Enums.Denominacion billete;

    @Schema(description = "Cantidad de billetes", example = "10")
    @NotNull(message = "La cantidad de billetes es requerida")
    @Min(value = 1, message = "La cantidad de billetes debe ser mayor a cero")
    @Max(value = 999999, message = "La cantidad de billetes excede el límite permitido")
    private Integer cantidadBilletes;

    @Schema(description = "Monto total de la denominación", example = "1000.00")
    @NotNull(message = "El monto es requerido")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a cero")
    @DecimalMax(value = "999999999999.99", message = "El monto excede el límite permitido")
    private BigDecimal monto;

    @Schema(description = "Versión del registro para control de concurrencia")
    private Long version;

    // Constructor vacío
    public DenominacionTransaccionDTO() {
    }

    // Constructor con parámetros
    public DenominacionTransaccionDTO(Enums.Denominacion billete, Integer cantidadBilletes, BigDecimal monto) {
        this.billete = billete;
        this.cantidadBilletes = cantidadBilletes;
        this.monto = monto;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTransaccionId() {
        return transaccionId;
    }

    public void setTransaccionId(Integer transaccionId) {
        this.transaccionId = transaccionId;
    }

    public Enums.Denominacion getBillete() {
        return billete;
    }

    public void setBillete(Enums.Denominacion billete) {
        this.billete = billete;
    }

    public Integer getCantidadBilletes() {
        return cantidadBilletes;
    }

    public void setCantidadBilletes(Integer cantidadBilletes) {
        this.cantidadBilletes = cantidadBilletes;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
} 