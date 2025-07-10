package com.conjunta.simbana.service;

import com.conjunta.simbana.exception.BusinessException;
import com.conjunta.simbana.exception.NotFoundException;
import com.conjunta.simbana.model.TurnoCaja;
import com.conjunta.simbana.model.TransaccionTurno;
import com.conjunta.simbana.model.Enums;
import com.conjunta.simbana.repository.TurnoCajaRepository;
import com.conjunta.simbana.repository.TransaccionTurnoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class TurnoCajaService {

    private final TurnoCajaRepository turnoCajaRepository;
    private final TransaccionTurnoRepository transaccionTurnoRepository;

    public TurnoCajaService(TurnoCajaRepository turnoCajaRepository, 
                           TransaccionTurnoRepository transaccionTurnoRepository) {
        this.turnoCajaRepository = turnoCajaRepository;
        this.transaccionTurnoRepository = transaccionTurnoRepository;
    }

    @Transactional
    public TurnoCaja iniciarTurno(String codigoTurno, String codigoCaja, String codigoCajero, 
                                 BigDecimal montoInicial) {
        
        if (montoInicial == null || montoInicial.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("El monto inicial debe ser mayor a cero", 1001);
        }

        if (turnoCajaRepository.existsByCodigoCajaAndEstado(codigoCaja, Enums.EstadoTurno.ABIERTO)) {
            throw new BusinessException("Ya existe un turno abierto para la caja: " + codigoCaja, 1002);
        }

        if (turnoCajaRepository.existsByCodigoCajeroAndEstado(codigoCajero, Enums.EstadoTurno.ABIERTO)) {
            throw new BusinessException("El cajero: " + codigoCajero + " ya tiene un turno abierto", 1003);
        }

        TurnoCaja turno = new TurnoCaja(codigoTurno);
        turno.setCodigoCaja(codigoCaja);
        turno.setCodigoCajero(codigoCajero);
        turno.setInicioTurno(LocalDateTime.now());
        turno.setMontoInicial(montoInicial);
        turno.setEstado(Enums.EstadoTurno.ABIERTO);

        TurnoCaja turnoGuardado = turnoCajaRepository.save(turno);

        TransaccionTurno transaccionInicio = new TransaccionTurno();
        transaccionInicio.setCodigoTurno(codigoTurno);
        transaccionInicio.setCodigoCaja(codigoCaja);
        transaccionInicio.setCodigoCajero(codigoCajero);
        transaccionInicio.setTipoTransaccion(Enums.TipoTransaccion.INICIO);
        transaccionInicio.setMontoTotal(montoInicial);
        transaccionInicio.setFechaHora(LocalDateTime.now());

        transaccionTurnoRepository.save(transaccionInicio);

        return turnoGuardado;
    }

    @Transactional
    public TurnoCaja cerrarTurno(String codigoTurno, BigDecimal montoFinal) {
        
        Optional<TurnoCaja> turnoOptional = turnoCajaRepository.findById(codigoTurno);
        if (turnoOptional.isEmpty()) {
            throw new NotFoundException("No se encontró el turno con código: " + codigoTurno, 2001);
        }

        TurnoCaja turno = turnoOptional.get();

        if (turno.getEstado() != Enums.EstadoTurno.ABIERTO) {
            throw new BusinessException("El turno: " + codigoTurno + " no está abierto", 2002);
        }

        if (montoFinal == null || montoFinal.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("El monto final debe ser mayor o igual a cero", 2003);
        }

        BigDecimal montoEsperado = calcularMontoEsperado(codigoTurno);

        if (montoFinal.compareTo(montoEsperado) != 0) {
            BigDecimal diferencia = montoFinal.subtract(montoEsperado);
            System.err.println("ALERTA: Discrepancia en turno " + codigoTurno + 
                             ". Monto final: " + montoFinal + 
                             ", Monto esperado: " + montoEsperado + 
                             ", Diferencia: " + diferencia);
        }

        turno.setFinTurno(LocalDateTime.now());
        turno.setMontoFinal(montoFinal);
        turno.setEstado(Enums.EstadoTurno.CERRADO);

        TurnoCaja turnoCerrado = turnoCajaRepository.save(turno);

        TransaccionTurno transaccionCierre = new TransaccionTurno();
        transaccionCierre.setCodigoTurno(codigoTurno);
        transaccionCierre.setCodigoCaja(turno.getCodigoCaja());
        transaccionCierre.setCodigoCajero(turno.getCodigoCajero());
        transaccionCierre.setTipoTransaccion(Enums.TipoTransaccion.CIERRE);
        transaccionCierre.setMontoTotal(montoFinal);
        transaccionCierre.setFechaHora(LocalDateTime.now());

        transaccionTurnoRepository.save(transaccionCierre);

        return turnoCerrado;
    }

    @Transactional(readOnly = true)
    public TurnoCaja findByCodigoTurno(String codigoTurno) {
        return turnoCajaRepository.findById(codigoTurno)
                .orElseThrow(() -> new NotFoundException("No se encontró el turno con código: " + codigoTurno, 3001));
    }

    @Transactional(readOnly = true)
    public List<TurnoCaja> findTurnosAbiertosByCaja(String codigoCaja) {
        return turnoCajaRepository.findByCodigoCajaAndEstado(codigoCaja, Enums.EstadoTurno.ABIERTO);
    }

    @Transactional(readOnly = true)
    public List<TurnoCaja> findTurnosAbiertosByCajero(String codigoCajero) {
        return turnoCajaRepository.findByCodigoCajeroAndEstado(codigoCajero, Enums.EstadoTurno.ABIERTO);
    }

    @Transactional(readOnly = true)
    public boolean existsTurnoAbiertoByCaja(String codigoCaja) {
        return turnoCajaRepository.existsByCodigoCajaAndEstado(codigoCaja, Enums.EstadoTurno.ABIERTO);
    }

    @Transactional(readOnly = true)
    public boolean existsTurnoAbiertoByCajero(String codigoCajero) {
        return turnoCajaRepository.existsByCodigoCajeroAndEstado(codigoCajero, Enums.EstadoTurno.ABIERTO);
    }

    private BigDecimal calcularMontoEsperado(String codigoTurno) {
        BigDecimal montoInicial = turnoCajaRepository.findById(codigoTurno)
                .map(TurnoCaja::getMontoInicial)
                .orElse(BigDecimal.ZERO);

        BigDecimal totalTransacciones = transaccionTurnoRepository.findByCodigoTurno(codigoTurno)
                .stream()
                .filter(t -> t.getTipoTransaccion() != Enums.TipoTransaccion.INICIO && 
                           t.getTipoTransaccion() != Enums.TipoTransaccion.CIERRE)
                .map(TransaccionTurno::getMontoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return montoInicial.add(totalTransacciones);
    }
} 