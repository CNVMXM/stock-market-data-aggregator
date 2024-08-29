package com.cnvmxm.portfolioservice.controller;

import com.cnvmxm.portfolioservice.client.PriceServiceClient;
import com.cnvmxm.portfolioservice.service.ClientService;
import com.cnvmxm.portfolioservice.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/mystocks")
@RequiredArgsConstructor
public class ClientStocksController {

    private final PriceServiceClient priceServiceClient;
    private final StockService stockService;
    private final ClientService clientService;

    @GetMapping("/{id}")
    public HashMap<String, Double> returnClientStocks(
            @PathVariable Long id
    ) {

        return priceServiceClient.getMyStocksPrices(stockService.getUserStocks(id));
    }

    @DeleteMapping("/{clientId}/{stockName}/delete")
    public void deleteStockFromClient(
            @PathVariable String stockName,
            @PathVariable Long clientId
    ) {
        clientService.removeStockFromClient(stockName, clientId);
    }
}
