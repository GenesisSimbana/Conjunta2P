package com.conjunta.simbana.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entidad que representa un turno de caja bancaria
 */
@Document(collection = "turnos_caja")
public class TurnoCaja {
    
    @Id
    private String id;
    
    @Field("turno_caja_id")
    private TurnoCajaId turnoCajaId;
    
    @Field("fecha_inicio")
    private LocalDateTime fechaInicio;
    
    @Field("fecha_fin")
    private LocalDateTime fechaFin;
    
    @Field("monto_inicial")
    private BigDecimal montoInicial;
    
    @Field("monto_final")
    private BigDecimal montoFinal;
    
    @Field("estado")
    private EstadoTurno estado;
    
    @Version
    @Field("version")
    private Long version;
    
    // Constructor vacío
    public TurnoCaja() {
    }
    
    // Constructor con clave primaria
    public TurnoCaja(TurnoCajaId turnoCajaId) {
        this.turnoCajaId = turnoCajaId;
    }
    
    // Constructor completo
    public TurnoCaja(TurnoCajaId turnoCajaId, LocalDateTime fechaInicio, BigDecimal montoInicial) {
        this.turnoCajaId = turnoCajaId;
        this.fechaInicio = fechaInicio;
        this.montoInicial = montoInicial;
        this.estado = EstadoTurno.ABIERTO;
        this.version = 0L;
    }
    
    // Getters y Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public TurnoCajaId getTurnoCajaId() {
        return turnoCajaId;
    }
    
    public void setTurnoCajaId(TurnoCajaId turnoCajaId) {
        this.turnoCajaId = turnoCajaId;
    }
    
    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }
    
    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    
    public LocalDateTime getFechaFin() {
        return fechaFin;
    }
    
    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    public BigDecimal getMontoInicial() {
        return montoInicial;
    }
    
    public void setMontoInicial(BigDecimal montoInicial) {
        this.montoInicial = montoInicial;
    }
    
    public BigDecimal getMontoFinal() {
        return montoFinal;
    }
    
    public void setMontoFinal(BigDecimal montoFinal) {
        this.montoFinal = montoFinal;
    }
    
    public EstadoTurno getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoTurno estado) {
        this.estado = estado;
    }
    
    public Long getVersion() {
        return version;
    }
    
    public void setVersion(Long version) {
        this.version = version;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TurnoCaja turnoCaja = (TurnoCaja) obj;
        return Objects.equals(id, turnoCaja.id) &&
               Objects.equals(turnoCajaId, turnoCaja.turnoCajaId) &&
               Objects.equals(fechaInicio, turnoCaja.fechaInicio) &&
               Objects.equals(fechaFin, turnoCaja.fechaFin) &&
               Objects.equals(montoInicial, turnoCaja.montoInicial) &&
               Objects.equals(montoFinal, turnoCaja.montoFinal) &&
               estado == turnoCaja.estado &&
               Objects.equals(version, turnoCaja.version);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, turnoCajaId, fechaInicio, fechaFin, montoInicial, montoFinal, estado, version);
    }
    
    @Override
    public String toString() {
        return "TurnoCaja{" +
                "id='" + id + '\'' +
                ", turnoCajaId=" + turnoCajaId +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", montoInicial=" + montoInicial +
                ", montoFinal=" + montoFinal +
                ", estado=" + estado +
                ", version=" + version +
                '}';
    }
    
    /**
     * Enum para el estado del turno
     */
    public enum EstadoTurno {
        ABIERTO,
        CERRADO
    }
} 