package com.cnvmxm.priceservice.client;

import com.cnvmxm.priceservice.model.dto.SymbolDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "aggregator-service", path = "/aggregator", url = "http://localhost:8081")
public interface PriceUpdateClient {

    @GetMapping
    ResponseEntity<List<SymbolDTO>> pollStocks();

}
