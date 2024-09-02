package com.cnvmxm.priceservice.service;

import com.cnvmxm.priceservice.client.PriceUpdateClient;
import com.cnvmxm.priceservice.converter.SymbolToPriceConverter;
import com.cnvmxm.priceservice.model.dto.SymbolDTO;
import com.cnvmxm.priceservice.model.entity.Price;
import com.cnvmxm.priceservice.repository.PriceRepository;
import com.cnvmxm.priceservice.util.ProcessPriceToMapUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceProviderService {

    private final PriceRepository priceRepository;
    private final PriceUpdateClient priceUpdateClient;
    private final ProcessPriceToMapUtil processPriceToMapUtil;
    private final SymbolToPriceConverter symbolToPriceConverter;

    public HashMap<String, BigDecimal> provideStocks() {

        ResponseEntity<List<SymbolDTO>> responseEntity = priceUpdateClient.pollStocks();
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            List<SymbolDTO> response = responseEntity.getBody();
            if(response != null) {
                List<Price> prices = symbolToPriceConverter.convertSymbolToPrice(response);
                priceRepository.saveAll(prices);
                return processPriceToMapUtil.priceToMap(priceRepository.findAll());
            } else {
                throw new IllegalArgumentException("Недопустимое значение: response равен null");
            }
        } else {
            // Обработка ошибки
            System.out.println("Ошибка получения данных: " + responseEntity.getStatusCode());
            return new HashMap<>();
        }
    }

    public HashMap<String, BigDecimal> provideMyStocks(List<String> myStocksList) {

        ResponseEntity<List<SymbolDTO>> responseEntity = priceUpdateClient.pollStocks();
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            List<SymbolDTO> response = responseEntity.getBody();
            if(response != null) {
                List<Price> prices = symbolToPriceConverter.convertSymbolToPrice(response);
                priceRepository.saveAll(prices);
                return processPriceToMapUtil.priceToMap(priceRepository.findByStockNameIn(myStocksList));
            } else {
                throw new IllegalArgumentException("Недопустимое значение: response равен null");
            }
        } else {
            // Обработка ошибки
            System.out.println("Ошибка получения данных: " + responseEntity.getStatusCode());
            return new HashMap<>();
        }


    }
}
