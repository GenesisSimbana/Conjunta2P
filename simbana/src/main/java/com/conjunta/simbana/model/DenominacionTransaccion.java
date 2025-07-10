package com.conjunta.simbana.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "denominaciones_transaccion", schema = "banquito")
public class DenominacionTransaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "transaccion_id", nullable = false)
    private Integer transaccionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "billete", nullable = false)
    private Enums.Denominacion billete;

    @Column(name = "cantidad_billetes", nullable = false)
    private Integer cantidadBilletes;

    @Column(name = "monto", nullable = false, precision = 14, scale = 2)
    private BigDecimal monto;

    @Version
    @Column(name = "version")
    private Long version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaccion_id", referencedColumnName = "id", insertable = false, updatable = false)
    private TransaccionTurno transaccionTurno;

    public DenominacionTransaccion() {
    }

    public DenominacionTransaccion(Integer id) {
        this.id = id;
    }

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

    public TransaccionTurno getTransaccionTurno() {
        return transaccionTurno;
    }

    public void setTransaccionTurno(TransaccionTurno transaccionTurno) {
        this.transaccionTurno = transaccionTurno;
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
        DenominacionTransaccion other = (DenominacionTransaccion) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "DenominacionTransaccion [id=" + id + ", transaccionId=" + transaccionId + ", billete=" + billete
                + ", cantidadBilletes=" + cantidadBilletes + ", monto=" + monto + ", version=" + version + "]";
    }
} 