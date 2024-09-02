package com.cnvmxm.priceservice.controller;

import com.cnvmxm.priceservice.service.PriceProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/price")
public class PriceController {

    private final PriceProviderService priceProviderService;

    @GetMapping()
    public HashMap<String, BigDecimal> getStocks() {
        return priceProviderService.provideStocks();
    }

    @GetMapping("/client")
    public HashMap<String, BigDecimal> getMyStocksPrices(List<String> myStocksList) {
        return priceProviderService.provideMyStocks(myStocksList);
    }
}
