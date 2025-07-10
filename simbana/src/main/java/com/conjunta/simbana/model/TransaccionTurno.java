package com.conjunta.simbana.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entidad que representa una transacción de turno de caja
 */
@Document(collection = "transacciones_turno")
public class TransaccionTurno {
    
    @Id
    private String id;
    
    @Field("transaccion_turno_id")
    private TransaccionTurnoId transaccionTurnoId;
    
    @Field("tipo_transaccion")
    private TipoTransaccion tipoTransaccion;
    
    @Field("monto_total")
    private BigDecimal montoTotal;
    
    @Field("fecha_transaccion")
    private LocalDateTime fechaTransaccion;
    
    @Field("denominaciones")
    private List<DenominacionTurno> denominaciones;
    
    // Constructor vacío
    public TransaccionTurno() {
        this.denominaciones = new ArrayList<>();
    }
    
    // Constructor con clave primaria
    public TransaccionTurno(TransaccionTurnoId transaccionTurnoId) {
        this.transaccionTurnoId = transaccionTurnoId;
        this.denominaciones = new ArrayList<>();
    }
    
    // Constructor completo
    public TransaccionTurno(TransaccionTurnoId transaccionTurnoId, TipoTransaccion tipoTransaccion, 
                           BigDecimal montoTotal, List<DenominacionTurno> denominaciones) {
        this.transaccionTurnoId = transaccionTurnoId;
        this.tipoTransaccion = tipoTransaccion;
        this.montoTotal = montoTotal;
        this.fechaTransaccion = LocalDateTime.now();
        this.denominaciones = denominaciones != null ? denominaciones : new ArrayList<>();
    }
    
    // Getters y Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public TransaccionTurnoId getTransaccionTurnoId() {
        return transaccionTurnoId;
    }
    
    public void setTransaccionTurnoId(TransaccionTurnoId transaccionTurnoId) {
        this.transaccionTurnoId = transaccionTurnoId;
    }
    
    public TipoTransaccion getTipoTransaccion() {
        return tipoTransaccion;
    }
    
    public void setTipoTransaccion(TipoTransaccion tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }
    
    public BigDecimal getMontoTotal() {
        return montoTotal;
    }
    
    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }
    
    public LocalDateTime getFechaTransaccion() {
        return fechaTransaccion;
    }
    
    public void setFechaTransaccion(LocalDateTime fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }
    
    public List<DenominacionTurno> getDenominaciones() {
        return denominaciones;
    }
    
    public void setDenominaciones(List<DenominacionTurno> denominaciones) {
        this.denominaciones = denominaciones != null ? denominaciones : new ArrayList<>();
    }
    
    // Método para agregar denominación
    public void agregarDenominacion(DenominacionTurno denominacion) {
        if (this.denominaciones == null) {
            this.denominaciones = new ArrayList<>();
        }
        this.denominaciones.add(denominacion);
    }
    
    // Método para calcular el monto total de las denominaciones
    public BigDecimal calcularMontoTotalDenominaciones() {
        if (denominaciones == null || denominaciones.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return denominaciones.stream()
                .map(DenominacionTurno::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TransaccionTurno that = (TransaccionTurno) obj;
        return Objects.equals(id, that.id) &&
               Objects.equals(transaccionTurnoId, that.transaccionTurnoId) &&
               tipoTransaccion == that.tipoTransaccion &&
               Objects.equals(montoTotal, that.montoTotal) &&
               Objects.equals(fechaTransaccion, that.fechaTransaccion) &&
               Objects.equals(denominaciones, that.denominaciones);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, transaccionTurnoId, tipoTransaccion, montoTotal, fechaTransaccion, denominaciones);
    }
    
    @Override
    public String toString() {
        return "TransaccionTurno{" +
                "id='" + id + '\'' +
                ", transaccionTurnoId=" + transaccionTurnoId +
                ", tipoTransaccion=" + tipoTransaccion +
                ", montoTotal=" + montoTotal +
                ", fechaTransaccion=" + fechaTransaccion +
                ", denominaciones=" + denominaciones +
                '}';
    }
    
    /**
     * Enum para el tipo de transacción
     */
    public enum TipoTransaccion {
        INICIO,
        AHORRO,
        DEPOSITO,
        CIERRE
    }
} 