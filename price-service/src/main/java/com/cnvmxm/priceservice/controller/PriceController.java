package com.cnvmxm.priceservice.controller;

import com.cnvmxm.priceservice.service.PriceProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class PriceController {

    private final PriceProviderService priceProviderService;

    @GetMapping("/price")
    public HashMap<String, Double> getStocks() {
        return priceProviderService.provideStocks();
    }

}
