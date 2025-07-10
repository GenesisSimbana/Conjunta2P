package com.conjunta.simbana.controller.mapper;

import com.conjunta.simbana.controller.dto.DenominacionTurnoDto;
import com.conjunta.simbana.model.DenominacionTransaccion;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface DenominacionTurnoMapper {
    DenominacionTurnoDto toDTO(DenominacionTransaccion model);
    DenominacionTransaccion toModel(DenominacionTurnoDto dto);
} 