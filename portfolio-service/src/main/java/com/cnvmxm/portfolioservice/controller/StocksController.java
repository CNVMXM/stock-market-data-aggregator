package com.cnvmxm.portfolioservice.controller;

import com.cnvmxm.portfolioservice.client.PriceServiceClient;
import com.cnvmxm.portfolioservice.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class StocksController {

    private final PriceServiceClient priceServiceClient;
    private final ClientService clientService;

    @GetMapping
    public HashMap<String, Double> returnAllStocks() {
        return priceServiceClient.getStocks();
    }

    @PatchMapping("/{clientId}/{stockName}")
    public void addStockToClient(
            @PathVariable Long clientId,
            @PathVariable String stockName
    ) {
        clientService.addStockToClient(stockName, clientId);
    }
}
