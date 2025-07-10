package com.conjunta.simbana.repository;

import com.conjunta.simbana.model.TurnoCaja;
import com.conjunta.simbana.model.TurnoCajaId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad TurnoCaja
 */
@Repository
public interface TurnoCajaRepository extends MongoRepository<TurnoCaja, String> {
    
    /**
     * Busca un turno por su clave compuesta
     */
    @Query("{'turnoCajaId.codigoCaja': ?0, 'turnoCajaId.codigoCajero': ?1, 'turnoCajaId.fecha': ?2}")
    Optional<TurnoCaja> findByTurnoCajaId(String codigoCaja, String codigoCajero, String fecha);
    
    /**
     * Busca turnos abiertos por caja y cajero
     */
    @Query("{'turnoCajaId.codigoCaja': ?0, 'turnoCajaId.codigoCajero': ?1, 'estado': 'ABIERTO'}")
    List<TurnoCaja> findTurnosAbiertosByCajaAndCajero(String codigoCaja, String codigoCajero);
    
    /**
     * Busca turnos por caja, cajero y fecha
     */
    @Query("{'turnoCajaId.codigoCaja': ?0, 'turnoCajaId.codigoCajero': ?1, 'turnoCajaId.fecha': ?2}")
    List<TurnoCaja> findByCajaCajeroAndFecha(String codigoCaja, String codigoCajero, String fecha);
    
    /**
     * Verifica si existe un turno abierto para la caja y cajero especificados
     */
    @Query("{'turnoCajaId.codigoCaja': ?0, 'turnoCajaId.codigoCajero': ?1, 'estado': 'ABIERTO'}")
    boolean existsByCajaAndCajeroAndEstadoAbierto(String codigoCaja, String codigoCajero);
} 