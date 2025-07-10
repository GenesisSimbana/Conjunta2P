package com.conjunta.simbana.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacciones_turno")
public class TransaccionTurno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "codigo_turno", length = 30, nullable = false)
    private String codigoTurno;

    @Column(name = "codigo_caja", length = 10, nullable = false)
    private String codigoCaja;

    @Column(name = "codigo_cajero", length = 10, nullable = false)
    private String codigoCajero;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_transaccion", nullable = false)
    private Enums.TipoTransaccion tipoTransaccion;

    @Column(name = "monto_total", nullable = false, precision = 14, scale = 2)
    private BigDecimal montoTotal;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Version
    @Column(name = "version")
    private Long version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_turno", referencedColumnName = "codigo_turno", insertable = false, updatable = false)
    private TurnoCaja turnoCaja;

    public TransaccionTurno() {
    }

    public TransaccionTurno(Integer id) {
        this.id = id;
    }

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

    public TurnoCaja getTurnoCaja() {
        return turnoCaja;
    }

    public void setTurnoCaja(TurnoCaja turnoCaja) {
        this.turnoCaja = turnoCaja;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TransaccionTurno other = (TransaccionTurno) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TransaccionTurno [id=" + id + ", codigoTurno=" + codigoTurno + ", codigoCaja=" + codigoCaja
                + ", codigoCajero=" + codigoCajero + ", tipoTransaccion=" + tipoTransaccion + ", montoTotal="
                + montoTotal + ", fechaHora=" + fechaHora + ", version=" + version + "]";
    }
} 