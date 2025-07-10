package com.conjunta.simbana.controller.mapper;

import com.conjunta.simbana.controller.dto.DenominacionTurnoDto;
import com.conjunta.simbana.model.DenominacionTransaccion;
import com.conjunta.simbana.model.Enums;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring")
public interface DenominacionTurnoMapper {
    
    DenominacionTurnoMapper INSTANCE = Mappers.getMapper(DenominacionTurnoMapper.class);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "billete", source = "denominacion")
    @Mapping(target = "cantidadBilletes", source = "cantidad")
    @Mapping(target = "monto", expression = "java(denominacionTurnoDto.getDenominacion().multiply(java.math.BigDecimal.valueOf(denominacionTurnoDto.getCantidad())))")
    @Mapping(target = "transaccionId", ignore = true)
    @Mapping(target = "transaccionTurno", ignore = true)
    @Mapping(target = "version", ignore = true)
    DenominacionTransaccion toEntity(DenominacionTurnoDto denominacionTurnoDto);
    
    @Mapping(target = "denominacion", source = "billete")
    @Mapping(target = "cantidad", source = "cantidadBilletes")
    DenominacionTurnoDto toDto(DenominacionTransaccion denominacionTurno);
    
    List<DenominacionTransaccion> toEntityList(List<DenominacionTurnoDto> denominacionTurnoDtoList);
    
    List<DenominacionTurnoDto> toDtoList(List<DenominacionTransaccion> denominacionTurnoList);

    default Enums.Denominacion map(BigDecimal value) {
        if (value == null) return null;
        return Enums.Denominacion.fromValor(value.stripTrailingZeros().toPlainString());
    }

    default BigDecimal map(Enums.Denominacion value) {
        if (value == null) return null;
        return new BigDecimal(value.getValor());
    }
} 