package com.cnvmxm.portfolioservice.service;

import com.cnvmxm.portfolioservice.model.entity.Client;
import com.cnvmxm.portfolioservice.model.entity.Stock;
import com.cnvmxm.portfolioservice.repository.ClientRepository;
import com.cnvmxm.portfolioservice.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final StockRepository stockRepository;

    public void addStockToClient(String stockName, Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow();
        Stock stock = stockRepository.findStockByStockName(stockName).orElseThrow();
        client.getStocks().add(stock);
        clientRepository.save(client);
    }

    public void removeStockFromClient(String stockName, Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow();
        Stock stock = stockRepository.findStockByStockName(stockName).orElseThrow();
        client.getStocks().remove(stock);
        clientRepository.save(client);
    }
}
