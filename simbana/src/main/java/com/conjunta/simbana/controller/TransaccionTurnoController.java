package com.conjunta.simbana.controller;

import com.conjunta.simbana.controller.dto.RegistrarTransaccionDto;
import com.conjunta.simbana.exception.BusinessException;
import com.conjunta.simbana.exception.NotFoundException;
import com.conjunta.simbana.model.TransaccionTurno;
import com.conjunta.simbana.model.TransaccionTurno.TipoTransaccion;
import com.conjunta.simbana.service.TransaccionTurnoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador para gestionar las transacciones de turno
 */
@RestController
@RequestMapping("/v1/cashboxes/transacciones")
@Tag(name = "Transacciones de Turno", description = "API para gestionar transacciones de turno de caja")
@CrossOrigin(origins = "*")
public class TransaccionTurnoController {
    
    private final TransaccionTurnoService transaccionTurnoService;
    
    @Autowired
    public TransaccionTurnoController(TransaccionTurnoService transaccionTurnoService) {
        this.transaccionTurnoService = transaccionTurnoService;
    }
    
    /**
     * Registra una nueva transacción de turno
     */
    @PostMapping
    @Operation(summary = "Registrar transacción", description = "Registra una nueva transacción de turno con denominaciones")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Transacción registrada exitosamente",
                    content = @Content(schema = @Schema(implementation = TransaccionTurno.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o reglas de negocio violadas"),
        @ApiResponse(responseCode = "404", description = "Turno no encontrado")
    })
    public ResponseEntity<TransaccionTurno> registrarTransaccion(
            @Parameter(description = "Datos de la transacción", required = true)
            @Valid @RequestBody RegistrarTransaccionDto registrarTransaccionDto) {
        
        TransaccionTurno transaccion = transaccionTurnoService.registrarTransaccion(registrarTransaccionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(transaccion);
    }
    
    /**
     * Busca transacciones por caja, cajero y turno
     */
    @GetMapping
    @Operation(summary = "Buscar transacciones", description = "Busca transacciones por caja, cajero y turno")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transacciones encontradas",
                    content = @Content(schema = @Schema(implementation = TransaccionTurno.class)))
    })
    public ResponseEntity<List<TransaccionTurno>> buscarTransacciones(
            @Parameter(description = "Código de la caja", required = true)
            @RequestParam String codigoCaja,
            @Parameter(description = "Código del cajero", required = true)
            @RequestParam String codigoCajero,
            @Parameter(description = "Código del turno", required = true)
            @RequestParam String codigoTurno) {
        
        List<TransaccionTurno> transacciones = transaccionTurnoService.buscarTransacciones(codigoCaja, codigoCajero, codigoTurno);
        return ResponseEntity.ok(transacciones);
    }
    
    /**
     * Busca transacciones por tipo
     */
    @GetMapping("/por-tipo")
    @Operation(summary = "Buscar transacciones por tipo", description = "Busca transacciones por caja, cajero, turno y tipo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transacciones encontradas",
                    content = @Content(schema = @Schema(implementation = TransaccionTurno.class)))
    })
    public ResponseEntity<List<TransaccionTurno>> buscarTransaccionesPorTipo(
            @Parameter(description = "Código de la caja", required = true)
            @RequestParam String codigoCaja,
            @Parameter(description = "Código del cajero", required = true)
            @RequestParam String codigoCajero,
            @Parameter(description = "Código del turno", required = true)
            @RequestParam String codigoTurno,
            @Parameter(description = "Tipo de transacción", required = true)
            @RequestParam TipoTransaccion tipoTransaccion) {
        
        List<TransaccionTurno> transacciones = transaccionTurnoService.buscarTransaccionesPorTipo(
            codigoCaja, codigoCajero, codigoTurno, tipoTransaccion);
        return ResponseEntity.ok(transacciones);
    }
    
    /**
     * Manejador de excepciones para BusinessException
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, String>> handleBusinessException(BusinessException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    
    /**
     * Manejador de excepciones para NotFoundException
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFoundException(NotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
} 