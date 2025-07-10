package com.conjunta.simbana.controller;

import com.conjunta.simbana.controller.dto.TransaccionTurnoDTO;
import com.conjunta.simbana.controller.mapper.TransaccionTurnoMapper;
import com.conjunta.simbana.controller.mapper.DenominacionTransaccionMapper;
import com.conjunta.simbana.exception.BusinessException;
import com.conjunta.simbana.exception.NotFoundException;
import com.conjunta.simbana.model.TransaccionTurno;
import com.conjunta.simbana.model.DenominacionTransaccion;
import com.conjunta.simbana.service.TransaccionTurnoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/cashboxes/transacciones")
@Tag(name = "Transacciones de Turno", description = "API para gestión de transacciones de turno bancario")
public class TransaccionTurnoController {

    private static final Logger logger = LoggerFactory.getLogger(TransaccionTurnoController.class);

    private final TransaccionTurnoService transaccionTurnoService;
    private final TransaccionTurnoMapper transaccionTurnoMapper;
    private final DenominacionTransaccionMapper denominacionTransaccionMapper;

    public TransaccionTurnoController(TransaccionTurnoService transaccionTurnoService,
                                    TransaccionTurnoMapper transaccionTurnoMapper,
                                    DenominacionTransaccionMapper denominacionTransaccionMapper) {
        this.transaccionTurnoService = transaccionTurnoService;
        this.transaccionTurnoMapper = transaccionTurnoMapper;
        this.denominacionTransaccionMapper = denominacionTransaccionMapper;
    }

    @PostMapping
    @Operation(summary = "Registrar transacción", description = "Registra una nueva transacción de turno con validaciones de negocio")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Transacción registrada exitosamente",
                    content = @Content(schema = @Schema(implementation = TransaccionTurnoDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o regla de negocio violada"),
        @ApiResponse(responseCode = "404", description = "Turno no encontrado"),
        @ApiResponse(responseCode = "409", description = "Turno no está abierto")
    })
    public ResponseEntity<TransaccionTurnoDTO> registrarTransaccion(
            @Parameter(description = "Datos de la transacción", required = true)
            @Valid @RequestBody TransaccionTurnoDTO transaccionTurnoDTO) {
        
        logger.info("Registrando transacción para turno: {}, tipo: {}", 
                   transaccionTurnoDTO.getCodigoTurno(), transaccionTurnoDTO.getTipoTransaccion());

        try {
            List<DenominacionTransaccion> denominaciones = null;
            if (transaccionTurnoDTO.getDenominaciones() != null && !transaccionTurnoDTO.getDenominaciones().isEmpty()) {
                denominaciones = new ArrayList<>(transaccionTurnoDTO.getDenominaciones().size());
                for (var denominacionDTO : transaccionTurnoDTO.getDenominaciones()) {
                    denominaciones.add(denominacionTransaccionMapper.toModel(denominacionDTO));
                }
            }

            TransaccionTurno transaccion = transaccionTurnoService.registrarTransaccion(
                transaccionTurnoDTO.getCodigoTurno(),
                transaccionTurnoDTO.getCodigoCaja(),
                transaccionTurnoDTO.getCodigoCajero(),
                transaccionTurnoDTO.getTipoTransaccion(),
                transaccionTurnoDTO.getMontoTotal(),
                denominaciones
            );

            TransaccionTurnoDTO transaccionDTO = transaccionTurnoMapper.toDTO(transaccion);
            logger.info("Transacción registrada exitosamente: {}", transaccionDTO.getId());
            
            return ResponseEntity.status(HttpStatus.CREATED).body(transaccionDTO);
            
        } catch (BusinessException e) {
            logger.error("Error de negocio al registrar transacción: {}", e.getMessage());
            throw e;
        } catch (NotFoundException e) {
            logger.error("Turno no encontrado: {}", transaccionTurnoDTO.getCodigoTurno());
            throw e;
        }
    }

    @PostMapping("/deposito")
    @Operation(summary = "Registrar depósito", description = "Registra una transacción de depósito específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Depósito registrado exitosamente",
                    content = @Content(schema = @Schema(implementation = TransaccionTurnoDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o regla de negocio violada"),
        @ApiResponse(responseCode = "404", description = "Turno no encontrado")
    })
    public ResponseEntity<TransaccionTurnoDTO> registrarDeposito(
            @Parameter(description = "Datos del depósito", required = true)
            @Valid @RequestBody TransaccionTurnoDTO transaccionTurnoDTO) {
        
        logger.info("Registrando depósito para turno: {}, monto: {}", 
                   transaccionTurnoDTO.getCodigoTurno(), transaccionTurnoDTO.getMontoTotal());

        try {
            List<DenominacionTransaccion> denominaciones = null;
            if (transaccionTurnoDTO.getDenominaciones() != null && !transaccionTurnoDTO.getDenominaciones().isEmpty()) {
                denominaciones = new ArrayList<>(transaccionTurnoDTO.getDenominaciones().size());
                for (var denominacionDTO : transaccionTurnoDTO.getDenominaciones()) {
                    denominaciones.add(denominacionTransaccionMapper.toModel(denominacionDTO));
                }
            }

            TransaccionTurno transaccion = transaccionTurnoService.registrarDeposito(
                transaccionTurnoDTO.getCodigoTurno(),
                transaccionTurnoDTO.getCodigoCaja(),
                transaccionTurnoDTO.getCodigoCajero(),
                transaccionTurnoDTO.getMontoTotal(),
                denominaciones
            );

            TransaccionTurnoDTO transaccionDTO = transaccionTurnoMapper.toDTO(transaccion);
            return ResponseEntity.status(HttpStatus.CREATED).body(transaccionDTO);
            
        } catch (BusinessException e) {
            logger.error("Error de negocio al registrar depósito: {}", e.getMessage());
            throw e;
        } catch (NotFoundException e) {
            logger.error("Turno no encontrado: {}", transaccionTurnoDTO.getCodigoTurno());
            throw e;
        }
    }

    @PostMapping("/ahorro")
    @Operation(summary = "Registrar ahorro", description = "Registra una transacción de ahorro específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ahorro registrado exitosamente",
                    content = @Content(schema = @Schema(implementation = TransaccionTurnoDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o saldo insuficiente"),
        @ApiResponse(responseCode = "404", description = "Turno no encontrado")
    })
    public ResponseEntity<TransaccionTurnoDTO> registrarAhorro(
            @Parameter(description = "Datos del ahorro", required = true)
            @Valid @RequestBody TransaccionTurnoDTO transaccionTurnoDTO) {
        
        logger.info("Registrando ahorro para turno: {}, monto: {}", 
                   transaccionTurnoDTO.getCodigoTurno(), transaccionTurnoDTO.getMontoTotal());

        try {   
            List<DenominacionTransaccion> denominaciones = null;
            if (transaccionTurnoDTO.getDenominaciones() != null && !transaccionTurnoDTO.getDenominaciones().isEmpty()) {
                denominaciones = new ArrayList<>(transaccionTurnoDTO.getDenominaciones().size());
                for (var denominacionDTO : transaccionTurnoDTO.getDenominaciones()) {
                    denominaciones.add(denominacionTransaccionMapper.toModel(denominacionDTO));
                }
            }

            TransaccionTurno transaccion = transaccionTurnoService.registrarAhorro(
                transaccionTurnoDTO.getCodigoTurno(),
                transaccionTurnoDTO.getCodigoCaja(),
                transaccionTurnoDTO.getCodigoCajero(),
                transaccionTurnoDTO.getMontoTotal(),
                denominaciones
            );

            TransaccionTurnoDTO transaccionDTO = transaccionTurnoMapper.toDTO(transaccion);
            return ResponseEntity.status(HttpStatus.CREATED).body(transaccionDTO);
            
        } catch (BusinessException e) {
            logger.error("Error de negocio al registrar ahorro: {}", e.getMessage());
            throw e;
        } catch (NotFoundException e) {
            logger.error("Turno no encontrado: {}", transaccionTurnoDTO.getCodigoTurno());
            throw e;
        }
    }

    @GetMapping("/{transaccionId}")
    @Operation(summary = "Obtener transacción por ID", description = "Obtiene una transacción específica por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transacción encontrada",
                    content = @Content(schema = @Schema(implementation = TransaccionTurnoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Transacción no encontrada")
    })
    public ResponseEntity<TransaccionTurnoDTO> getTransaccionById(
            @Parameter(description = "ID de la transacción", required = true)
            @PathVariable Integer transaccionId) {
        
        logger.info("Consultando transacción: {}", transaccionId);

        try {
            TransaccionTurno transaccion = transaccionTurnoService.findById(transaccionId);
            TransaccionTurnoDTO transaccionDTO = transaccionTurnoMapper.toDTO(transaccion);
            return ResponseEntity.ok(transaccionDTO);
            
        } catch (NotFoundException e) {
            logger.error("Transacción no encontrada: {}", transaccionId);
            throw e;
        }
    }

    @GetMapping("/turno/{codigoTurno}")
    @Operation(summary = "Obtener transacciones por turno", description = "Obtiene todas las transacciones de un turno específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transacciones encontradas",
                    content = @Content(schema = @Schema(implementation = TransaccionTurnoDTO.class)))
    })
    public ResponseEntity<List<TransaccionTurnoDTO>> getTransaccionesByTurno(
            @Parameter(description = "Código del turno", required = true)
            @PathVariable String codigoTurno) {
        
        logger.info("Consultando transacciones para turno: {}", codigoTurno);

        List<TransaccionTurno> transacciones = transaccionTurnoService.findByCodigoTurno(codigoTurno);
        List<TransaccionTurnoDTO> transaccionesDTO = new ArrayList<>(transacciones.size());
        
        for (TransaccionTurno transaccion : transacciones) {
            transaccionesDTO.add(transaccionTurnoMapper.toDTO(transaccion));
        }
        
        return ResponseEntity.ok(transaccionesDTO);
    }

    @GetMapping("/turno/{codigoTurno}/saldo")
    @Operation(summary = "Obtener saldo del turno", description = "Calcula y obtiene el saldo actual de un turno")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Saldo calculado exitosamente")
    })
    public ResponseEntity<BigDecimal> getSaldoTurno(
            @Parameter(description = "Código del turno", required = true)
            @PathVariable String codigoTurno) {
        
        logger.info("Calculando saldo para turno: {}", codigoTurno);

        BigDecimal saldo = transaccionTurnoService.calcularSaldoTurno(codigoTurno);
        return ResponseEntity.ok(saldo);
    }

    @GetMapping("/{transaccionId}/denominaciones")
    @Operation(summary = "Obtener denominaciones de transacción", description = "Obtiene las denominaciones de una transacción específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Denominaciones encontradas")
    })
    public ResponseEntity<List<com.conjunta.simbana.controller.dto.DenominacionTransaccionDTO>> getDenominacionesByTransaccion(
            @Parameter(description = "ID de la transacción", required = true)
            @PathVariable Integer transaccionId) {
        
        logger.info("Consultando denominaciones para transacción: {}", transaccionId);

        List<DenominacionTransaccion> denominaciones = transaccionTurnoService.getDenominacionesByTransaccion(transaccionId);
        List<com.conjunta.simbana.controller.dto.DenominacionTransaccionDTO> denominacionesDTO = new ArrayList<>(denominaciones.size());
        
        for (DenominacionTransaccion denominacion : denominaciones) {
            denominacionesDTO.add(denominacionTransaccionMapper.toDTO(denominacion));
        }
        
        return ResponseEntity.ok(denominacionesDTO);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Void> handleNotFoundException(NotFoundException e) {
        logger.error("Recurso no encontrado: {}", e.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Void> handleBusinessException(BusinessException e) {
        logger.error("Error de negocio: {}", e.getMessage());
        return ResponseEntity.badRequest().build();
    }
} 