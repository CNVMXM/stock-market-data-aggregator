package com.cnvmxm.aggregatorservice.util;

import com.cnvmxm.aggregatorservice.client.NYSEGet;
import com.cnvmxm.aggregatorservice.model.dto.SymbolDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProcessCurrentRateResponse {

    public SymbolDTO processData(String jsonInput) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonInput);
            JsonNode globalQuoteNode = rootNode.get("Global Quote");

            SymbolDTO symbolDTO = new SymbolDTO();
            symbolDTO.setSymbol(globalQuoteNode.get("01. symbol").asText());
            symbolDTO.setHigh(new BigDecimal(globalQuoteNode.get("03. high").asText()));
            symbolDTO.setLow(new BigDecimal(globalQuoteNode.get("04. low").asText()));
            symbolDTO.setPrice(new BigDecimal(globalQuoteNode.get("05. price").asText()));
            symbolDTO.setVolume(new BigDecimal(globalQuoteNode.get("06. volume").asText()));
            symbolDTO.setLatestTradingDay(globalQuoteNode.get("07. latest trading day").asText());
            symbolDTO.setPreviousClose(new BigDecimal(globalQuoteNode.get("08. previous close").asText()));
            symbolDTO.setChange(new BigDecimal(globalQuoteNode.get("09. change").asText()));
            symbolDTO.setChangePercent(globalQuoteNode.get("10. change percent").asText());

            log.info("Symbol: " + symbolDTO);
            return symbolDTO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }
}
