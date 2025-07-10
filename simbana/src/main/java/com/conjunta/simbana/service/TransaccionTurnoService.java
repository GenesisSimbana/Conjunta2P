package com.conjunta.simbana.service;

import com.conjunta.simbana.controller.dto.RegistrarTransaccionDto;
import com.conjunta.simbana.controller.mapper.DenominacionTurnoMapper;
import com.conjunta.simbana.exception.BusinessException;
import com.conjunta.simbana.exception.NotFoundException;
import com.conjunta.simbana.model.DenominacionTurno;
import com.conjunta.simbana.model.TransaccionTurno;
import com.conjunta.simbana.model.TransaccionTurnoId;
import com.conjunta.simbana.model.TransaccionTurno.TipoTransaccion;
import com.conjunta.simbana.model.TurnoCaja;
import com.conjunta.simbana.repository.TransaccionTurnoRepository;
import com.conjunta.simbana.repository.TurnoCajaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Servicio para gestionar las transacciones de turno
 */
@Service
@Transactional
public class TransaccionTurnoService {
    
    private final TransaccionTurnoRepository transaccionTurnoRepository;
    private final TurnoCajaRepository turnoCajaRepository;
    private final DenominacionTurnoMapper denominacionTurnoMapper;
    
    @Autowired
    public TransaccionTurnoService(TransaccionTurnoRepository transaccionTurnoRepository,
                                  TurnoCajaRepository turnoCajaRepository,
                                  DenominacionTurnoMapper denominacionTurnoMapper) {
        this.transaccionTurnoRepository = transaccionTurnoRepository;
        this.turnoCajaRepository = turnoCajaRepository;
        this.denominacionTurnoMapper = denominacionTurnoMapper;
    }
    
    /**
     * Registra una nueva transacción de turno
     */
    public TransaccionTurno registrarTransaccion(RegistrarTransaccionDto registrarTransaccionDto) {
        // Validar que el turno exista y esté abierto
        TurnoCaja turnoCaja = validarTurnoAbierto(registrarTransaccionDto);
        
        // Validar que el monto total coincida con la suma de las denominaciones
        validarMontoTotal(registrarTransaccionDto);
        
        // Crear la clave compuesta de la transacción
        TransaccionTurnoId transaccionTurnoId = new TransaccionTurnoId(
            registrarTransaccionDto.getCodigoCaja(),
            registrarTransaccionDto.getCodigoCajero(),
            registrarTransaccionDto.getCodigoTurno(),
            UUID.randomUUID().toString()
        );
        
        // Convertir DTOs a entidades
        List<DenominacionTurno> denominaciones = denominacionTurnoMapper.toEntityList(
            registrarTransaccionDto.getDenominaciones()
        );
        
        // Crear la transacción
        TransaccionTurno transaccion = new TransaccionTurno(
            transaccionTurnoId,
            registrarTransaccionDto.getTipoTransaccion(),
            registrarTransaccionDto.getMontoTotal(),
            denominaciones
        );
        
        // Guardar la transacción
        return transaccionTurnoRepository.save(transaccion);
    }
    
    /**
     * Busca transacciones por caja, cajero y turno
     */
    @Transactional(readOnly = true)
    public List<TransaccionTurno> buscarTransacciones(String codigoCaja, String codigoCajero, String codigoTurno) {
        return transaccionTurnoRepository.findByCajaCajeroAndTurno(codigoCaja, codigoCajero, codigoTurno);
    }
    
    /**
     * Busca transacciones por tipo
     */
    @Transactional(readOnly = true)
    public List<TransaccionTurno> buscarTransaccionesPorTipo(String codigoCaja, String codigoCajero, 
                                                            String codigoTurno, TipoTransaccion tipoTransaccion) {
        return transaccionTurnoRepository.findByCajaCajeroTurnoAndTipo(codigoCaja, codigoCajero, codigoTurno, tipoTransaccion);
    }
    
    /**
     * Valida que el turno exista y esté abierto
     */
    private TurnoCaja validarTurnoAbierto(RegistrarTransaccionDto dto) {
        // Buscar el turno
        var turnoOpt = turnoCajaRepository.findByTurnoCajaId(dto.getCodigoCaja(), dto.getCodigoCajero(), dto.getCodigoTurno());
        if (turnoOpt.isEmpty()) {
            throw new NotFoundException("Turno", 
                String.format("caja: %s, cajero: %s, turno: %s", dto.getCodigoCaja(), dto.getCodigoCajero(), dto.getCodigoTurno()));
        }
        
        TurnoCaja turnoCaja = turnoOpt.get();
        
        // Validar que el turno esté abierto
        if (turnoCaja.getEstado() != TurnoCaja.EstadoTurno.ABIERTO) {
            throw new BusinessException("registrar transacción", "el turno está cerrado");
        }
        
        return turnoCaja;
    }
    
    /**
     * Valida que el monto total coincida con la suma de las denominaciones
     */
    private void validarMontoTotal(RegistrarTransaccionDto dto) {
        BigDecimal montoCalculado = dto.getDenominaciones().stream()
                .map(denom -> denom.getDenominacion().multiply(BigDecimal.valueOf(denom.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        if (dto.getMontoTotal().compareTo(montoCalculado) != 0) {
            throw new BusinessException("registrar transacción", 
                String.format("el monto total (%s) no coincide con la suma de las denominaciones (%s)", 
                    dto.getMontoTotal(), montoCalculado));
        }
    }
} 