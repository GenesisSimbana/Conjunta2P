package com.conjunta.simbana.repository;

import com.conjunta.simbana.model.TransaccionTurno;
import com.conjunta.simbana.model.Enums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransaccionTurnoRepository extends JpaRepository<TransaccionTurno, Integer> {
    List<TransaccionTurno> findByCodigoTurno(String codigoTurno);
    List<TransaccionTurno> findByCodigoCaja(String codigoCaja);
    List<TransaccionTurno> findByCodigoCajero(String codigoCajero);
    List<TransaccionTurno> findByTipoTransaccion(Enums.TipoTransaccion tipoTransaccion);
    List<TransaccionTurno> findByCodigoTurnoAndTipoTransaccion(String codigoTurno, Enums.TipoTransaccion tipoTransaccion);
    List<TransaccionTurno> findByCodigoCajaAndTipoTransaccion(String codigoCaja, Enums.TipoTransaccion tipoTransaccion);
    List<TransaccionTurno> findByCodigoCajeroAndTipoTransaccion(String codigoCajero, Enums.TipoTransaccion tipoTransaccion);
    List<TransaccionTurno> findByFechaHoraBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    List<TransaccionTurno> findByCodigoTurnoAndFechaHoraBetween(String codigoTurno, LocalDateTime fechaInicio, LocalDateTime fechaFin);
    List<TransaccionTurno> findByCodigoCajaAndFechaHoraBetween(String codigoCaja, LocalDateTime fechaInicio, LocalDateTime fechaFin);
    List<TransaccionTurno> findByCodigoCajeroAndFechaHoraBetween(String codigoCajero, LocalDateTime fechaInicio, LocalDateTime fechaFin);
    long countByCodigoTurno(String codigoTurno);
    long countByCodigoTurnoAndTipoTransaccion(String codigoTurno, Enums.TipoTransaccion tipoTransaccion);
} 