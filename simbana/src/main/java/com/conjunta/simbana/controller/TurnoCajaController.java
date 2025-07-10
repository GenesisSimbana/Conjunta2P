package com.conjunta.simbana.controller;

import com.conjunta.simbana.controller.dto.TurnoCajaDTO;
import com.conjunta.simbana.controller.dto.IniciarTurnoDTO;
import com.conjunta.simbana.controller.dto.CerrarTurnoDTO;
import com.conjunta.simbana.controller.mapper.TurnoCajaMapper;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/cashboxes/turnos")
@Tag(name = "Turnos de Caja", description = "API para gestión de turnos de caja bancaria")
public class TurnoCajaController {

    private static final Logger logger = LoggerFactory.getLogger(TurnoCajaController.class);

    private final TurnoCajaService turnoCajaService;
    private final TurnoCajaMapper turnoCajaMapper;

    public TurnoCajaController(TurnoCajaService turnoCajaService, TurnoCajaMapper turnoCajaMapper) {
        this.turnoCajaService = turnoCajaService;
        this.turnoCajaMapper = turnoCajaMapper;
    }

    @PostMapping
    @Operation(summary = "Iniciar turno", description = "Inicia un nuevo turno de caja con validaciones de negocio")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Turno iniciado exitosamente",
                    content = @Content(schema = @Schema(implementation = TurnoCajaDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o regla de negocio violada"),
        @ApiResponse(responseCode = "409", description = "Ya existe un turno abierto para la caja/cajero")
    })
    public ResponseEntity<TurnoCajaDTO> iniciarTurno(
            @Parameter(description = "Datos para iniciar el turno", required = true)
            @Valid @RequestBody IniciarTurnoDTO iniciarTurnoDTO) {
        
        logger.info("Iniciando turno para caja: {}, cajero: {}", 
                   iniciarTurnoDTO.getCodigoCaja(), iniciarTurnoDTO.getCodigoCajero());

        try {
            TurnoCaja turno = turnoCajaService.iniciarTurno(
                iniciarTurnoDTO.getCodigoTurno(),
                iniciarTurnoDTO.getCodigoCaja(),
                iniciarTurnoDTO.getCodigoCajero(),
                iniciarTurnoDTO.getMontoInicial()
            );

            TurnoCajaDTO turnoDTO = turnoCajaMapper.toDTO(turno);
            logger.info("Turno iniciado exitosamente: {}", turnoDTO.getCodigoTurno());
            
            return ResponseEntity.status(HttpStatus.CREATED).body(turnoDTO);
            
        } catch (BusinessException e) {
            logger.error("Error de negocio al iniciar turno: {}", e.getMessage());
            throw e;
        }
    }

    @PatchMapping("/{turnoId}/cerrar")
    @Operation(summary = "Cerrar turno", description = "Cierra un turno de caja con validación de saldo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Turno cerrado exitosamente",
                    content = @Content(schema = @Schema(implementation = TurnoCajaDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o regla de negocio violada"),
        @ApiResponse(responseCode = "404", description = "Turno no encontrado"),
        @ApiResponse(responseCode = "409", description = "Turno ya está cerrado")
    })
    public ResponseEntity<TurnoCajaDTO> cerrarTurno(
            @Parameter(description = "ID del turno a cerrar", required = true)
            @PathVariable String turnoId,
            @Parameter(description = "Datos para cerrar el turno", required = true)
            @Valid @RequestBody CerrarTurnoDTO cerrarTurnoDTO) {
        
        logger.info("Cerrando turno: {} con monto final: {}", turnoId, cerrarTurnoDTO.getMontoFinal());

        try {
            TurnoCaja turno = turnoCajaService.cerrarTurno(turnoId, cerrarTurnoDTO.getMontoFinal());
            TurnoCajaDTO turnoDTO = turnoCajaMapper.toDTO(turno);
            
            logger.info("Turno cerrado exitosamente: {}", turnoId);
            return ResponseEntity.ok(turnoDTO);
            
        } catch (NotFoundException e) {
            logger.error("Turno no encontrado: {}", turnoId);
            throw e;
        } catch (BusinessException e) {
            logger.error("Error de negocio al cerrar turno: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{turnoId}")
    @Operation(summary = "Obtener turno por ID", description = "Obtiene un turno específico por su código")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Turno encontrado",
                    content = @Content(schema = @Schema(implementation = TurnoCajaDTO.class))),
        @ApiResponse(responseCode = "404", description = "Turno no encontrado")
    })
    public ResponseEntity<TurnoCajaDTO> getTurnoById(
            @Parameter(description = "ID del turno", required = true)
            @PathVariable String turnoId) {
        
        logger.info("Consultando turno: {}", turnoId);

        try {
            TurnoCaja turno = turnoCajaService.findByCodigoTurno(turnoId);
            TurnoCajaDTO turnoDTO = turnoCajaMapper.toDTO(turno);
            return ResponseEntity.ok(turnoDTO);
            
        } catch (NotFoundException e) {
            logger.error("Turno no encontrado: {}", turnoId);
            throw e;
        }
    }

    @GetMapping("/caja/{codigoCaja}/abiertos")
    @Operation(summary = "Obtener turnos abiertos por caja", description = "Obtiene todos los turnos abiertos de una caja específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Turnos encontrados",
                    content = @Content(schema = @Schema(implementation = TurnoCajaDTO.class)))
    })
    public ResponseEntity<List<TurnoCajaDTO>> getTurnosAbiertosByCaja(
            @Parameter(description = "Código de la caja", required = true)
            @PathVariable String codigoCaja) {
        
        logger.info("Consultando turnos abiertos para caja: {}", codigoCaja);

        List<TurnoCaja> turnos = turnoCajaService.findTurnosAbiertosByCaja(codigoCaja);
        List<TurnoCajaDTO> turnosDTO = new ArrayList<>(turnos.size());
        
        for (TurnoCaja turno : turnos) {
            turnosDTO.add(turnoCajaMapper.toDTO(turno));
        }
        
        return ResponseEntity.ok(turnosDTO);
    }

    @GetMapping("/cajero/{codigoCajero}/abiertos")
    @Operation(summary = "Obtener turnos abiertos por cajero", description = "Obtiene todos los turnos abiertos de un cajero específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Turnos encontrados",
                    content = @Content(schema = @Schema(implementation = TurnoCajaDTO.class)))
    })
    public ResponseEntity<List<TurnoCajaDTO>> getTurnosAbiertosByCajero(
            @Parameter(description = "Código del cajero", required = true)
            @PathVariable String codigoCajero) {
        
        logger.info("Consultando turnos abiertos para cajero: {}", codigoCajero);

        List<TurnoCaja> turnos = turnoCajaService.findTurnosAbiertosByCajero(codigoCajero);
        List<TurnoCajaDTO> turnosDTO = new ArrayList<>(turnos.size());
        
        for (TurnoCaja turno : turnos) {
            turnosDTO.add(turnoCajaMapper.toDTO(turno));
        }
        
        return ResponseEntity.ok(turnosDTO);
    }

    @GetMapping("/caja/{codigoCaja}/verificar-abierto")
    @Operation(summary = "Verificar si existe turno abierto", description = "Verifica si existe un turno abierto para una caja específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Verificación completada")
    })
    public ResponseEntity<Boolean> verificarTurnoAbiertoByCaja(
            @Parameter(description = "Código de la caja", required = true)
            @PathVariable String codigoCaja) {
        
        logger.info("Verificando turno abierto para caja: {}", codigoCaja);

        boolean existeTurnoAbierto = turnoCajaService.existsTurnoAbiertoByCaja(codigoCaja);
        return ResponseEntity.ok(existeTurnoAbierto);
    }

    @GetMapping("/cajero/{codigoCajero}/verificar-abierto")
    @Operation(summary = "Verificar si existe turno abierto", description = "Verifica si existe un turno abierto para un cajero específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Verificación completada")
    })
    public ResponseEntity<Boolean> verificarTurnoAbiertoByCajero(
            @Parameter(description = "Código del cajero", required = true)
            @PathVariable String codigoCajero) {
        
        logger.info("Verificando turno abierto para cajero: {}", codigoCajero);

        boolean existeTurnoAbierto = turnoCajaService.existsTurnoAbiertoByCajero(codigoCajero);
        return ResponseEntity.ok(existeTurnoAbierto);
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