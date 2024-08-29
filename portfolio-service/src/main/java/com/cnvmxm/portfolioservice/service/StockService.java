package com.cnvmxm.portfolioservice.service;

import com.cnvmxm.portfolioservice.model.entity.Stock;
import com.cnvmxm.portfolioservice.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {

    private final ClientRepository clientRepository;

    public List<String> getUserStocks(Long id) {
        List<Stock> clientStocks = clientRepository.findStocksByClientId(id);
        List<String> stocksNames = new ArrayList<>();
        for(Stock stock : clientStocks) {
            stocksNames.add(stock.getStockName());
        }
        return stocksNames;
    }
}
