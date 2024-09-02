package com.cnvmxm.priceservice.util;

import com.cnvmxm.priceservice.model.entity.Price;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Component
public class ProcessPriceToMapUtil {

    public HashMap<String, BigDecimal> priceToMap(List<Price> prices) {

        HashMap<String, BigDecimal> stocksMap = new HashMap<>();

        for (Price price : prices) {
            stocksMap.put(price.getStockName(), price.getStockValue());
        }

        return stocksMap;
    }
}
