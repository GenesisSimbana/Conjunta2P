package com.conjunta.simbana.controller.mapper;

import com.conjunta.simbana.controller.dto.TransaccionTurnoDTO;
import com.conjunta.simbana.model.TransaccionTurno;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface TransaccionTurnoMapper {

    TransaccionTurnoDTO toDTO(TransaccionTurno model);
    
    TransaccionTurno toModel(TransaccionTurnoDTO dto);
} 