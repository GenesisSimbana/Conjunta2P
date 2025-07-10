package com.conjunta.simbana.service;

import com.conjunta.simbana.exception.BusinessException;
import com.conjunta.simbana.exception.NotFoundException;
import com.conjunta.simbana.model.*;
import com.conjunta.simbana.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TransaccionTurnoService {

    private final TransaccionTurnoRepository transaccionTurnoRepository;
    private final DenominacionTurnoRepository denominacionTurnoRepository;
    private final TurnoCajaRepository turnoCajaRepository;

    public TransaccionTurnoService(TransaccionTurnoRepository transaccionTurnoRepository,
                                  DenominacionTurnoRepository denominacionTurnoRepository,
                                  TurnoCajaRepository turnoCajaRepository) {
        this.transaccionTurnoRepository = transaccionTurnoRepository;
        this.denominacionTurnoRepository = denominacionTurnoRepository;
        this.turnoCajaRepository = turnoCajaRepository;
    }

    @Transactional
    public TransaccionTurno registrarTransaccion(String codigoTurno, String codigoCaja, String codigoCajero,
                                                Enums.TipoTransaccion tipoTransaccion, BigDecimal montoTotal,
                                                List<DenominacionTransaccion> denominaciones) {

        if (montoTotal == null || montoTotal.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("El monto total debe ser mayor a cero", 4001);
        }
        validarTurnoAbierto(codigoTurno);

        if (denominaciones != null && !denominaciones.isEmpty()) {
            BigDecimal totalDenominaciones = denominaciones.stream()
                    .map(DenominacionTransaccion::getMonto)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            if (totalDenominaciones.compareTo(montoTotal) != 0) {
                throw new BusinessException("El total de denominaciones (" + totalDenominaciones + 
                                          ") no coincide con el monto total (" + montoTotal + ")", 4002);
            }
        }

        validarTransaccionPorTipo(tipoTransaccion, montoTotal, codigoTurno);

        TransaccionTurno transaccion = new TransaccionTurno();
        transaccion.setCodigoTurno(codigoTurno);
        transaccion.setCodigoCaja(codigoCaja);
        transaccion.setCodigoCajero(codigoCajero);
        transaccion.setTipoTransaccion(tipoTransaccion);
        transaccion.setMontoTotal(montoTotal);
        transaccion.setFechaHora(LocalDateTime.now());

        TransaccionTurno transaccionGuardada = transaccionTurnoRepository.save(transaccion);

        if (denominaciones != null && !denominaciones.isEmpty()) {
            for (DenominacionTransaccion denominacion : denominaciones) {
                denominacion.setTransaccionId(transaccionGuardada.getId());
                denominacionTurnoRepository.save(denominacion);
            }
        }

        return transaccionGuardada;
    }

    @Transactional(readOnly = true)
    public TransaccionTurno findById(Integer id) {
        return transaccionTurnoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró la transacción con ID: " + id, 5001));
    }

    @Transactional(readOnly = true)
    public List<TransaccionTurno> findByCodigoTurno(String codigoTurno) {
        return transaccionTurnoRepository.findByCodigoTurno(codigoTurno);
    }

    @Transactional(readOnly = true)
    public List<TransaccionTurno> findByTipoTransaccion(Enums.TipoTransaccion tipoTransaccion) {
        return transaccionTurnoRepository.findByTipoTransaccion(tipoTransaccion);
    }

    @Transactional(readOnly = true)
    public List<TransaccionTurno> findByTurnoAndTipo(String codigoTurno, Enums.TipoTransaccion tipoTransaccion) {
        return transaccionTurnoRepository.findByCodigoTurnoAndTipoTransaccion(codigoTurno, tipoTransaccion);
    }

    @Transactional(readOnly = true)
    public BigDecimal calcularSaldoTurno(String codigoTurno) {
        BigDecimal montoInicial = turnoCajaRepository.findById(codigoTurno)
                .map(TurnoCaja::getMontoInicial)
                .orElse(BigDecimal.ZERO);

        List<TransaccionTurno> transacciones = transaccionTurnoRepository.findByCodigoTurno(codigoTurno)
                .stream()
                .filter(t -> t.getTipoTransaccion() != Enums.TipoTransaccion.INICIO && 
                           t.getTipoTransaccion() != Enums.TipoTransaccion.CIERRE)
                .toList();

        BigDecimal saldo = montoInicial;

        for (TransaccionTurno transaccion : transacciones) {
            switch (transaccion.getTipoTransaccion()) {
                case DEPOSITO:
                    saldo = saldo.add(transaccion.getMontoTotal());
                    break;
                case AHORRO:
                    saldo = saldo.subtract(transaccion.getMontoTotal());
                    break;
                default:
                    break;
            }
        }

        return saldo;
    }

    @Transactional(readOnly = true)
    public List<DenominacionTransaccion> getDenominacionesByTransaccion(Integer transaccionId) {
        return denominacionTurnoRepository.findByTransaccionId(transaccionId);
    }

    private TurnoCaja validarTurnoAbierto(String codigoTurno) {
        Optional<TurnoCaja> turnoOptional = turnoCajaRepository.findById(codigoTurno);
        if (turnoOptional.isEmpty()) {
            throw new NotFoundException("No se encontró el turno con código: " + codigoTurno, 6001);
        }

        TurnoCaja turno = turnoOptional.get();
        if (turno.getEstado() != Enums.EstadoTurno.ABIERTO) {
            throw new BusinessException("El turno: " + codigoTurno + " no está abierto", 6002);
        }

        return turno;
    }

    private void validarTransaccionPorTipo(Enums.TipoTransaccion tipoTransaccion, BigDecimal montoTotal, String codigoTurno) {
        switch (tipoTransaccion) {
            case INICIO:
                throw new BusinessException("No se puede registrar una transacción de INICIO manualmente", 7001);
            case CIERRE:
                throw new BusinessException("No se puede registrar una transacción de CIERRE manualmente", 7002);
            case AHORRO:    
                BigDecimal saldoActual = calcularSaldoTurno(codigoTurno);
                if (saldoActual.compareTo(montoTotal) < 0) {
                    throw new BusinessException("Saldo insuficiente. Saldo actual: " + saldoActual + 
                                              ", Monto solicitado: " + montoTotal, 7003);
                }
                break;
            case DEPOSITO:
                break;
            default:
                throw new BusinessException("Tipo de transacción no válido: " + tipoTransaccion, 7004);
        }
    }

    @Transactional
    public TransaccionTurno registrarDeposito(String codigoTurno, String codigoCaja, String codigoCajero,
                                             BigDecimal montoTotal, List<DenominacionTransaccion> denominaciones) {
        return registrarTransaccion(codigoTurno, codigoCaja, codigoCajero, 
                                   Enums.TipoTransaccion.DEPOSITO, montoTotal, denominaciones);
    }

    @Transactional
    public TransaccionTurno registrarAhorro(String codigoTurno, String codigoCaja, String codigoCajero,
                                           BigDecimal montoTotal, List<DenominacionTransaccion> denominaciones) {
        return registrarTransaccion(codigoTurno, codigoCaja, codigoCajero, 
                                   Enums.TipoTransaccion.AHORRO, montoTotal, denominaciones);
    }
} 