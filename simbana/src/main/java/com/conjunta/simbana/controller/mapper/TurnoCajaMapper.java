package com.conjunta.simbana.controller.mapper;

import com.conjunta.simbana.controller.dto.TurnoCajaDTO;
import com.conjunta.simbana.model.TurnoCaja;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface TurnoCajaMapper {

    TurnoCajaDTO toDTO(TurnoCaja model);
    
    TurnoCaja toModel(TurnoCajaDTO dto);
} 