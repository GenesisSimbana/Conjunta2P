package com.conjunta.simbana.repository;

import com.conjunta.simbana.model.TransaccionTurno;
import com.conjunta.simbana.model.Enums;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransaccionTurnoRepository extends MongoRepository<TransaccionTurno, String> {
    List<TransaccionTurno> findByTipoTransaccion(Enums.TipoTransaccion tipoTransaccion);
    List<TransaccionTurno> findByCodigoCajaAndTipoTransaccion(String codigoCaja, Enums.TipoTransaccion tipoTransaccion);
    List<TransaccionTurno> findByCodigoTurnoAndFechaHoraBetween(String codigoTurno, LocalDateTime fechaInicio, LocalDateTime fechaFin);
    List<TransaccionTurno> findByCodigoCajaAndFechaHoraBetween(String codigoCaja, LocalDateTime fechaInicio, LocalDateTime fechaFin);
   } 