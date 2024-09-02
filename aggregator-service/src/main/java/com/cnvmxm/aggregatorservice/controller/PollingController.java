package com.cnvmxm.aggregatorservice.controller;

import com.cnvmxm.aggregatorservice.mapper.SymbolMapper;
import com.cnvmxm.aggregatorservice.model.dto.SymbolDTO;
import com.cnvmxm.aggregatorservice.model.entity.Symbol;
import com.cnvmxm.aggregatorservice.repository.SymbolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/aggregator")
@RequiredArgsConstructor
public class PollingController {

    private final SymbolRepository symbolRepository;

    @GetMapping
    public List<SymbolDTO> pollStocks() {

        List<SymbolDTO> symbolDTOS = new ArrayList<>();
        List<Symbol> symbols = symbolRepository.findAll();
        for(Symbol symbol : symbols) {
            symbolDTOS.add(SymbolMapper.symbolMapper.toDto(symbol));
        }

        return symbolDTOS;

    }
}
