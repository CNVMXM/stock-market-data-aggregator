package com.cnvmxm.priceservice.converter;

import com.cnvmxm.priceservice.model.dto.SymbolDTO;
import com.cnvmxm.priceservice.model.entity.Price;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SymbolToPriceConverter {

    public List<Price> convertSymbolToPrice(List<SymbolDTO> symbolDTOS) {
        return symbolDTOS.stream()
                .map(symbolDTO -> new Price(symbolDTO.getSymbol(), symbolDTO.getPrice()))
                .collect(Collectors.toList());
    }
}
