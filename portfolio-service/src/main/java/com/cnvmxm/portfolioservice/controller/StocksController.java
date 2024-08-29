package com.cnvmxm.portfolioservice.controller;

import com.cnvmxm.portfolioservice.client.PriceServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class StocksController {

    private final PriceServiceClient priceServiceClient;

    @GetMapping
    public HashMap<String, Double> returnAllStocks() {
        return priceServiceClient.getStocks();
    }


}
