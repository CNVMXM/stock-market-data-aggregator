package com.cnvmxm.portfolioservice.controller;

import com.cnvmxm.portfolioservice.client.PriceServiceClient;
import com.cnvmxm.portfolioservice.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/mystocks")
@RequiredArgsConstructor
public class ClientStocksController {

    private final PriceServiceClient priceServiceClient;
    private final StockService stockService;

    @GetMapping("/{id}")
    public HashMap<String, Double> returnClientStocks(
            @PathVariable Long id
    ) {

        return priceServiceClient.getMyStocksPrices(stockService.getUserStocks(id));
    }
}
