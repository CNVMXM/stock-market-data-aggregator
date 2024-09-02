package com.cnvmxm.aggregatorservice.mapper;

import com.cnvmxm.aggregatorservice.model.dto.QuoteDTO;
import com.cnvmxm.aggregatorservice.model.entity.Quote;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface QuoteMapper {

    QuoteMapper quoteMapper = Mappers.getMapper(QuoteMapper.class);

    Quote toEntity(QuoteDTO quoteDTO);

    QuoteDTO toDto(Quote quote);
}
