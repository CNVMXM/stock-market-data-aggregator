package com.cnvmxm.portfolioservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;

@FeignClient(name="price-service", path="/price", url="http://localhost:60000")
public interface PriceServiceClient {

    @GetMapping()
    HashMap<String, Double> getStocks();

    @GetMapping("/client")
    HashMap<String, Double> getMyStocksPrices(List<String> myStocksList);
}
