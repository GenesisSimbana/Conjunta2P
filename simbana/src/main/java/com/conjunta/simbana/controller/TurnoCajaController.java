package com.conjunta.simbana.controller;

import com.conjunta.simbana.controller.dto.IniciarTurnoDto;
import com.conjunta.simbana.exception.BusinessException;
import com.conjunta.simbana.exception.NotFoundException;
import com.conjunta.simbana.model.TurnoCaja;
import com.conjunta.simbana.service.TurnoCajaService;
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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador para gestionar los turnos de caja
 */
@RestController
@RequestMapping("/v1/cashboxes/turnos")
@Tag(name = "Turnos de Caja", description = "API para gestionar turnos de caja bancaria")
@CrossOrigin(origins = "*")
public class TurnoCajaController {
    
    private final TurnoCajaService turnoCajaService;
    
    @Autowired
    public TurnoCajaController(TurnoCajaService turnoCajaService) {
        this.turnoCajaService = turnoCajaService;
    }
    
    /**
     * Inicia un nuevo turno de caja
     */
    @PostMapping
    @Operation(summary = "Iniciar turno", description = "Inicia un nuevo turno de caja con monto inicial")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Turno iniciado exitosamente",
                    content = @Content(schema = @Schema(implementation = TurnoCaja.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "409", description = "Ya existe un turno abierto para la caja y cajero")
    })
    public ResponseEntity<TurnoCaja> iniciarTurno(
            @Parameter(description = "Datos para iniciar el turno", required = true)
            @Valid @RequestBody IniciarTurnoDto iniciarTurnoDto) {
        
        TurnoCaja turnoCaja = turnoCajaService.iniciarTurno(iniciarTurnoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(turnoCaja);
    }
    
    /**
     * Cierra un turno de caja
     */
    @PatchMapping("/{turnoId}/cerrar")
    @Operation(summary = "Cerrar turno", description = "Cierra un turno de caja con validación de saldo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Turno cerrado exitosamente",
                    content = @Content(schema = @Schema(implementation = TurnoCaja.class))),
        @ApiResponse(responseCode = "404", description = "Turno no encontrado"),
        @ApiResponse(responseCode = "400", description = "El turno ya está cerrado o hay discrepancia en el saldo")
    })
    public ResponseEntity<TurnoCaja> cerrarTurno(
            @Parameter(description = "ID del turno (formato: codigoCaja-codigoCajero-fecha)", required = true)
            @PathVariable String turnoId,
            @Parameter(description = "Monto final del turno", required = true)
            @RequestParam BigDecimal montoFinal) {
        
        // Parsear el turnoId (formato: codigoCaja-codigoCajero-fecha)
        String[] partes = turnoId.split("-");
        if (partes.length != 3) {
            throw new BusinessException("cerrar turno", "formato de turnoId inválido. Debe ser: codigoCaja-codigoCajero-fecha");
        }
        
        String codigoCaja = partes[0];
        String codigoCajero = partes[1];
        String fecha = partes[2];
        
        TurnoCaja turnoCaja = turnoCajaService.cerrarTurno(codigoCaja, codigoCajero, fecha, montoFinal);
        return ResponseEntity.ok(turnoCaja);
    }
    
    /**
     * Busca un turno por su ID
     */
    @GetMapping("/{turnoId}")
    @Operation(summary = "Buscar turno", description = "Busca un turno por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Turno encontrado",
                    content = @Content(schema = @Schema(implementation = TurnoCaja.class))),
        @ApiResponse(responseCode = "404", description = "Turno no encontrado")
    })
    public ResponseEntity<TurnoCaja> buscarTurno(
            @Parameter(description = "ID del turno (formato: codigoCaja-codigoCajero-fecha)", required = true)
            @PathVariable String turnoId) {
        
        // Parsear el turnoId
        String[] partes = turnoId.split("-");
        if (partes.length != 3) {
            throw new BusinessException("buscar turno", "formato de turnoId inválido. Debe ser: codigoCaja-codigoCajero-fecha");
        }
        
        String codigoCaja = partes[0];
        String codigoCajero = partes[1];
        String fecha = partes[2];
        
        TurnoCaja turnoCaja = turnoCajaService.buscarTurno(codigoCaja, codigoCajero, fecha);
        return ResponseEntity.ok(turnoCaja);
    }
    
    /**
     * Busca turnos abiertos por caja y cajero
     */
    @GetMapping("/abiertos")
    @Operation(summary = "Buscar turnos abiertos", description = "Busca turnos abiertos por caja y cajero")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Turnos encontrados",
                    content = @Content(schema = @Schema(implementation = TurnoCaja.class)))
    })
    public ResponseEntity<List<TurnoCaja>> buscarTurnosAbiertos(
            @Parameter(description = "Código de la caja", required = true)
            @RequestParam String codigoCaja,
            @Parameter(description = "Código del cajero", required = true)
            @RequestParam String codigoCajero) {
        
        List<TurnoCaja> turnos = turnoCajaService.buscarTurnosAbiertos(codigoCaja, codigoCajero);
        return ResponseEntity.ok(turnos);
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