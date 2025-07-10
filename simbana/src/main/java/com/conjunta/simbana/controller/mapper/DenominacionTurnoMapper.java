package com.conjunta.simbana.controller.mapper;

import com.conjunta.simbana.controller.dto.DenominacionTurnoDto;
import com.conjunta.simbana.model.DenominacionTurno;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapper para convertir entre DenominacionTurno y DenominacionTurnoDto
 */
@Mapper(componentModel = "spring")
public interface DenominacionTurnoMapper {
    
    DenominacionTurnoMapper INSTANCE = Mappers.getMapper(DenominacionTurnoMapper.class);
    
    /**
     * Convierte DenominacionTurnoDto a DenominacionTurno
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "monto", expression = "java(denominacionTurnoDto.getDenominacion().multiply(java.math.BigDecimal.valueOf(denominacionTurnoDto.getCantidad())))")
    DenominacionTurno toEntity(DenominacionTurnoDto denominacionTurnoDto);
    
    /**
     * Convierte DenominacionTurno a DenominacionTurnoDto
     */
    DenominacionTurnoDto toDto(DenominacionTurno denominacionTurno);
    
    /**
     * Convierte lista de DenominacionTurnoDto a lista de DenominacionTurno
     */
    List<DenominacionTurno> toEntityList(List<DenominacionTurnoDto> denominacionTurnoDtoList);
    
    /**
     * Convierte lista de DenominacionTurno a lista de DenominacionTurnoDto
     */
    List<DenominacionTurnoDto> toDtoList(List<DenominacionTurno> denominacionTurnoList);
} 