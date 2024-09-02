package com.cnvmxm.aggregatorservice.mapper;

import com.cnvmxm.aggregatorservice.model.dto.SymbolDTO;
import com.cnvmxm.aggregatorservice.model.entity.Symbol;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SymbolMapper {

    SymbolMapper symbolMapper = Mappers.getMapper(SymbolMapper.class);

    Symbol toEntity(SymbolDTO symbolDTO);

    SymbolDTO toDto(Symbol symbol);
}