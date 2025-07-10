package com.conjunta.simbana.service;

import com.conjunta.simbana.controller.dto.DenominacionTurnoDto;
import com.conjunta.simbana.controller.dto.IniciarTurnoDto;
import com.conjunta.simbana.controller.mapper.DenominacionTurnoMapper;
import com.conjunta.simbana.exception.BusinessException;
import com.conjunta.simbana.exception.NotFoundException;
import com.conjunta.simbana.model.DenominacionTurno;
import com.conjunta.simbana.model.TurnoCaja;
import com.conjunta.simbana.model.TurnoCajaId;
import com.conjunta.simbana.model.TransaccionTurno;
import com.conjunta.simbana.model.TransaccionTurnoId;
import com.conjunta.simbana.model.TransaccionTurno.TipoTransaccion;
import com.conjunta.simbana.repository.TurnoCajaRepository;
import com.conjunta.simbana.repository.TransaccionTurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Servicio para gestionar los turnos de caja
 */
@Service
@Transactional
public class TurnoCajaService {
    
    private final TurnoCajaRepository turnoCajaRepository;
    private final TransaccionTurnoRepository transaccionTurnoRepository;
    private final DenominacionTurnoMapper denominacionTurnoMapper;
    
    @Autowired
    public TurnoCajaService(TurnoCajaRepository turnoCajaRepository, 
                           TransaccionTurnoRepository transaccionTurnoRepository,
                           DenominacionTurnoMapper denominacionTurnoMapper) {
        this.turnoCajaRepository = turnoCajaRepository;
        this.transaccionTurnoRepository = transaccionTurnoRepository;
        this.denominacionTurnoMapper = denominacionTurnoMapper;
    }
    
    /**
     * Inicia un nuevo turno de caja
     */
    public TurnoCaja iniciarTurno(IniciarTurnoDto iniciarTurnoDto) {
        // Validar que no exista un turno abierto para la misma caja y cajero
        if (turnoCajaRepository.existsByCajaAndCajeroAndEstadoAbierto(
                iniciarTurnoDto.getCodigoCaja(), iniciarTurnoDto.getCodigoCajero())) {
            throw new BusinessException("iniciar turno", 
                "ya existe un turno abierto para la caja " + iniciarTurnoDto.getCodigoCaja() + 
                " y cajero " + iniciarTurnoDto.getCodigoCajero());
        }
        
        // Crear la clave compuesta del turno
        TurnoCajaId turnoCajaId = new TurnoCajaId(
            iniciarTurnoDto.getCodigoCaja(),
            iniciarTurnoDto.getCodigoCajero(),
            iniciarTurnoDto.getFecha()
        );
        
        // Crear el turno
        TurnoCaja turnoCaja = new TurnoCaja(
            turnoCajaId,
            LocalDateTime.now(),
            iniciarTurnoDto.getMontoInicial()
        );
        
        // Guardar el turno
        TurnoCaja turnoCajaGuardado = turnoCajaRepository.save(turnoCaja);
        
        // Registrar la transacción de inicio si hay denominaciones iniciales
        if (iniciarTurnoDto.getDenominacionesIniciales() != null && 
            !iniciarTurnoDto.getDenominacionesIniciales().isEmpty()) {
            
            registrarTransaccionInicio(turnoCajaGuardado, iniciarTurnoDto.getDenominacionesIniciales());
        }
        
        return turnoCajaGuardado;
    }
    
    /**
     * Cierra un turno de caja
     */
    public TurnoCaja cerrarTurno(String codigoCaja, String codigoCajero, String fecha, BigDecimal montoFinal) {
        // Buscar el turno
        Optional<TurnoCaja> turnoOpt = turnoCajaRepository.findByTurnoCajaId(codigoCaja, codigoCajero, fecha);
        if (turnoOpt.isEmpty()) {
            throw new NotFoundException("Turno", 
                String.format("caja: %s, cajero: %s, fecha: %s", codigoCaja, codigoCajero, fecha));
        }
        
        TurnoCaja turnoCaja = turnoOpt.get();
        
        // Validar que el turno esté abierto
        if (turnoCaja.getEstado() != TurnoCaja.EstadoTurno.ABIERTO) {
            throw new BusinessException("cerrar turno", "el turno ya está cerrado");
        }
        
        // Calcular el monto esperado basado en las transacciones
        BigDecimal montoEsperado = calcularMontoEsperado(turnoCaja);
        
        // Validar que el monto final coincida con el esperado
        if (montoFinal.compareTo(montoEsperado) != 0) {
            // Aquí se podría generar una alerta o registrar la discrepancia
            throw new BusinessException("cerrar turno", 
                String.format("el monto final (%s) no coincide con el esperado (%s)", montoFinal, montoEsperado));
        }
        
        // Cerrar el turno
        turnoCaja.setMontoFinal(montoFinal);
        turnoCaja.setFechaFin(LocalDateTime.now());
        turnoCaja.setEstado(TurnoCaja.EstadoTurno.CERRADO);
        
        return turnoCajaRepository.save(turnoCaja);
    }
    
    /**
     * Busca un turno por su clave compuesta
     */
    @Transactional(readOnly = true)
    public TurnoCaja buscarTurno(String codigoCaja, String codigoCajero, String fecha) {
        return turnoCajaRepository.findByTurnoCajaId(codigoCaja, codigoCajero, fecha)
                .orElseThrow(() -> new NotFoundException("Turno", 
                    String.format("caja: %s, cajero: %s, fecha: %s", codigoCaja, codigoCajero, fecha)));
    }
    
    /**
     * Busca turnos abiertos por caja y cajero
     */
    @Transactional(readOnly = true)
    public List<TurnoCaja> buscarTurnosAbiertos(String codigoCaja, String codigoCajero) {
        return turnoCajaRepository.findTurnosAbiertosByCajaAndCajero(codigoCaja, codigoCajero);
    }
    
    /**
     * Registra la transacción de inicio del turno
     */
    private void registrarTransaccionInicio(TurnoCaja turnoCaja, List<DenominacionTurnoDto> denominacionesDto) {
        // Convertir DTOs a entidades
        List<DenominacionTurno> denominaciones = denominacionTurnoMapper.toEntityList(denominacionesDto);
        
        // Crear la transacción de inicio
        TransaccionTurno transaccion = new TransaccionTurno();
        transaccion.setTransaccionTurnoId(new TransaccionTurnoId(
            turnoCaja.getTurnoCajaId().getCodigoCaja(),
            turnoCaja.getTurnoCajaId().getCodigoCajero(),
            turnoCaja.getTurnoCajaId().getFecha(),
            UUID.randomUUID().toString()
        ));
        transaccion.setTipoTransaccion(TipoTransaccion.INICIO);
        transaccion.setMontoTotal(turnoCaja.getMontoInicial());
        transaccion.setDenominaciones(denominaciones);
        transaccion.setFechaTransaccion(LocalDateTime.now());
        
        transaccionTurnoRepository.save(transaccion);
    }
    
    /**
     * Calcula el monto esperado basado en las transacciones del turno
     */
    private BigDecimal calcularMontoEsperado(TurnoCaja turnoCaja) {
        List<TransaccionTurno> transacciones = transaccionTurnoRepository.findByCajaCajeroAndTurno(
            turnoCaja.getTurnoCajaId().getCodigoCaja(),
            turnoCaja.getTurnoCajaId().getCodigoCajero(),
            turnoCaja.getTurnoCajaId().getFecha()
        );
        
        BigDecimal montoEsperado = turnoCaja.getMontoInicial();
        
        for (TransaccionTurno transaccion : transacciones) {
            if (transaccion.getTipoTransaccion() == TipoTransaccion.DEPOSITO) {
                montoEsperado = montoEsperado.add(transaccion.getMontoTotal());
            } else if (transaccion.getTipoTransaccion() == TipoTransaccion.AHORRO) {
                montoEsperado = montoEsperado.subtract(transaccion.getMontoTotal());
            }
        }
        
        return montoEsperado;
    }
} 