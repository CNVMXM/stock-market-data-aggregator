package com.cnvmxm.priceservice.service;

import com.cnvmxm.priceservice.model.entity.Price;
import com.cnvmxm.priceservice.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceProviderService {

    private final PriceRepository priceRepository;

    public HashMap<String, Double> provideStocks() {

        List<Price> prices =  priceRepository.findAll();

        HashMap<String, Double> stocksMap = new HashMap<>();

        for (Price price : prices) {
            stocksMap.put(price.getStockName(), price.getStockValue());
        }

        return stocksMap;
    }
}
