package com.conjunta.simbana.repository;

import com.conjunta.simbana.model.TransaccionTurno;
import com.conjunta.simbana.model.TransaccionTurno.TipoTransaccion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad TransaccionTurno
 */
@Repository
public interface TransaccionTurnoRepository extends MongoRepository<TransaccionTurno, String> {
    
    /**
     * Busca una transacción por su clave compuesta
     */
    @Query("{'transaccionTurnoId.codigoCaja': ?0, 'transaccionTurnoId.codigoCajero': ?1, 'transaccionTurnoId.codigoTurno': ?2, 'transaccionTurnoId.codigoTransaccion': ?3}")
    Optional<TransaccionTurno> findByTransaccionTurnoId(String codigoCaja, String codigoCajero, String codigoTurno, String codigoTransaccion);
    
    /**
     * Busca transacciones por caja, cajero y turno
     */
    @Query("{'transaccionTurnoId.codigoCaja': ?0, 'transaccionTurnoId.codigoCajero': ?1, 'transaccionTurnoId.codigoTurno': ?2}")
    List<TransaccionTurno> findByCajaCajeroAndTurno(String codigoCaja, String codigoCajero, String codigoTurno);
    
    /**
     * Busca transacciones por caja, cajero, turno y tipo
     */
    @Query("{'transaccionTurnoId.codigoCaja': ?0, 'transaccionTurnoId.codigoCajero': ?1, 'transaccionTurnoId.codigoTurno': ?2, 'tipoTransaccion': ?3}")
    List<TransaccionTurno> findByCajaCajeroTurnoAndTipo(String codigoCaja, String codigoCajero, String codigoTurno, TipoTransaccion tipoTransaccion);
    
    /**
     * Busca transacciones por fecha de transacción
     */
    @Query("{'fechaTransaccion': {$gte: ?0, $lte: ?1}}")
    List<TransaccionTurno> findByFechaTransaccionBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    /**
     * Busca transacciones de un turno específico en un rango de fechas
     */
    @Query("{'transaccionTurnoId.codigoCaja': ?0, 'transaccionTurnoId.codigoCajero': ?1, 'transaccionTurnoId.codigoTurno': ?2, 'fechaTransaccion': {$gte: ?3, $lte: ?4}}")
    List<TransaccionTurno> findByCajaCajeroTurnoAndFechaBetween(String codigoCaja, String codigoCajero, String codigoTurno, LocalDateTime fechaInicio, LocalDateTime fechaFin);
} 