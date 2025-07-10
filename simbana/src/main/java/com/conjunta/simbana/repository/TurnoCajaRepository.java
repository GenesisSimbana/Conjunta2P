package com.conjunta.simbana.repository;

import com.conjunta.simbana.model.TurnoCaja;
import com.conjunta.simbana.model.Enums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TurnoCajaRepository extends JpaRepository<TurnoCaja, String> {
    List<TurnoCaja> findByCodigoCaja(String codigoCaja);
    List<TurnoCaja> findByCodigoCajero(String codigoCajero);
    List<TurnoCaja> findByEstado(Enums.EstadoTurno estado);
    List<TurnoCaja> findByCodigoCajaAndEstado(String codigoCaja, Enums.EstadoTurno estado);
    List<TurnoCaja> findByCodigoCajeroAndEstado(String codigoCajero, Enums.EstadoTurno estado);
    List<TurnoCaja> findByInicioTurnoBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    List<TurnoCaja> findByFinTurnoBetweenAndEstado(LocalDateTime fechaInicio, LocalDateTime fechaFin, Enums.EstadoTurno estado);
    boolean existsByCodigoCajaAndEstado(String codigoCaja, Enums.EstadoTurno estado);
    boolean existsByCodigoCajeroAndEstado(String codigoCajero, Enums.EstadoTurno estado);
    List<TurnoCaja> findTop1ByCodigoCajaOrderByInicioTurnoDesc(String codigoCaja);
    List<TurnoCaja> findTop1ByCodigoCajeroOrderByInicioTurnoDesc(String codigoCajero);
} 