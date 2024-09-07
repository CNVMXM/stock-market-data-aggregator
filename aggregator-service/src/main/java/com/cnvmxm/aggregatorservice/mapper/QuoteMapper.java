package com.cnvmxm.aggregatorservice.mapper;

import com.cnvmxm.aggregatorservice.model.dto.QuoteDTO;
import com.cnvmxm.aggregatorservice.model.entity.Quote;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface QuoteMapper {

    QuoteMapper quoteMapper = Mappers.getMapper(QuoteMapper.class);

    Quote toEntity(QuoteDTO quoteDTO);

    QuoteDTO toDto(Quote quote);

    default List<Quote> toEntityList(List<QuoteDTO> quoteDTOList) {
        if (quoteDTOList == null) {
            return null;
        }
        return quoteDTOList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
