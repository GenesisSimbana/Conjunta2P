package com.conjunta.simbana.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "turnos_caja")
public class TurnoCaja {

    @Id
    @Column(name = "codigo_turno", length = 30, nullable = false)
    private String codigoTurno;

    @Column(name = "codigo_caja", length = 10, nullable = false)
    private String codigoCaja;

    @Column(name = "codigo_cajero", length = 10, nullable = false)
    private String codigoCajero;

    @Column(name = "inicio_turno", nullable = false)
    private LocalDateTime inicioTurno;

    @Column(name = "monto_inicial", nullable = false, precision = 14, scale = 2)
    private BigDecimal montoInicial;

    @Column(name = "fin_turno")
    private LocalDateTime finTurno;

    @Column(name = "monto_final", precision = 14, scale = 2)
    private BigDecimal montoFinal;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Enums.EstadoTurno estado;

    @Version
    @Column(name = "version")
    private Long version;

    public TurnoCaja() {
    }

    public TurnoCaja(String codigoTurno) {
        this.codigoTurno = codigoTurno;
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

    // equals y hashCode solo con propiedades de clave primaria
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((codigoTurno == null) ? 0 : codigoTurno.hashCode());
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
        TurnoCaja other = (TurnoCaja) obj;
        if (codigoTurno == null) {
            if (other.codigoTurno != null)
                return false;
        } else if (!codigoTurno.equals(other.codigoTurno))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TurnoCaja [codigoTurno=" + codigoTurno + ", codigoCaja=" + codigoCaja + ", codigoCajero=" + codigoCajero
                + ", inicioTurno=" + inicioTurno + ", montoInicial=" + montoInicial + ", finTurno=" + finTurno
                + ", montoFinal=" + montoFinal + ", estado=" + estado + ", version=" + version + "]";
    }
} 