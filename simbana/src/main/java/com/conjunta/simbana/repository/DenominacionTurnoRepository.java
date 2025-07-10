package com.conjunta.simbana.repository;

import com.conjunta.simbana.model.DenominacionTransaccion;
import com.conjunta.simbana.model.Enums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DenominacionTurnoRepository extends JpaRepository<DenominacionTransaccion, Integer> {
    List<DenominacionTransaccion> findByTransaccionId(Integer transaccionId);
    List<DenominacionTransaccion> findByBillete(Enums.Denominacion billete);
    List<DenominacionTransaccion> findByTransaccionIdAndBillete(Integer transaccionId, Enums.Denominacion billete);
    long countByTransaccionId(Integer transaccionId);
    long countByTransaccionIdAndBillete(Integer transaccionId, Enums.Denominacion billete);
    List<DenominacionTransaccion> findByCantidadBilletesGreaterThan(Integer cantidad);
    List<DenominacionTransaccion> findByMontoGreaterThan(java.math.BigDecimal monto);
    List<DenominacionTransaccion> findByTransaccionIdAndCantidadBilletesGreaterThan(Integer transaccionId, Integer cantidad);
    List<DenominacionTransaccion> findByTransaccionIdAndMontoGreaterThan(Integer transaccionId, java.math.BigDecimal monto);
} 