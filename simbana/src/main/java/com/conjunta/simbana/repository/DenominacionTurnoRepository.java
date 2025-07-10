package com.conjunta.simbana.repository;

import com.conjunta.simbana.model.DenominacionTransaccion;
import com.conjunta.simbana.model.Enums;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DenominacionTurnoRepository extends MongoRepository<DenominacionTransaccion, String> {
    List<DenominacionTransaccion> findByTransaccionId(Integer transaccionId);
    List<DenominacionTransaccion> findByBillete(Enums.Denominacion billete);
    List<DenominacionTransaccion> findByTransaccionIdAndBillete(Integer transaccionId, Enums.Denominacion billete);
    List<DenominacionTransaccion> findByTransaccionIdAndCantidadBilletesGreaterThan(Integer transaccionId, Integer cantidad);
    List<DenominacionTransaccion> findByTransaccionIdAndMontoGreaterThan(Integer transaccionId, java.math.BigDecimal monto);
} 