package com.cnvmxm.aggregatorservice.service;

import com.cnvmxm.aggregatorservice.client.NYSEGet;
import com.cnvmxm.aggregatorservice.mapper.SymbolMapper;
import com.cnvmxm.aggregatorservice.model.dto.SymbolDTO;
import com.cnvmxm.aggregatorservice.repository.QuoteRepository;
import com.cnvmxm.aggregatorservice.repository.SymbolRepository;
import com.cnvmxm.aggregatorservice.util.ProcessCurrentRateResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class AlphaVantageQuoteClientService {

    @Value("${nyse.api-key}")
    private String apiKey;

    private List<String> symbols;
    private boolean initialized = false;

    private final ExecutorService fetchStockQuotesExecutorService;
    private final ExecutorService processStockQuotesExecutorService;
    private final NYSEGet nyseGet;
    private final ProcessCurrentRateResponse processCurrentRateResponse;
    private final SymbolRepository symbolRepo;
    private final QuoteRepository quoteRepo;
    private final AlphaVantageListingClientService alphaVantageListingClientService;

    public AlphaVantageQuoteClientService(
            NYSEGet nyseGet,
            ProcessCurrentRateResponse processCurrentRateResponse,
            SymbolRepository symbolRepo,
            QuoteRepository quoteRepo,
            AlphaVantageListingClientService alphaVantageListingClientService
    ) {
        fetchStockQuotesExecutorService = Executors.newFixedThreadPool(2);
        processStockQuotesExecutorService = Executors.newFixedThreadPool(2);
        this.nyseGet = nyseGet;
        this.processCurrentRateResponse = processCurrentRateResponse;
        this.symbolRepo = symbolRepo;
        this.quoteRepo = quoteRepo;
        this.alphaVantageListingClientService = alphaVantageListingClientService;
    }

    @PostConstruct
    public void init() {

        updateListingStatus();
        updateSymbols();
        initialized = true;
        // запуск метода fetchDataTask() после инициализации
        fetchDataTask();
    }

    @Scheduled(fixedDelay = 259200000) // раз в день
    public void updateSymbols() {
        symbols = quoteRepo.findAllSymbols();
    }

    @Scheduled(fixedDelay = 86400000) // раз в три дня
    public void updateListingStatus() {
        alphaVantageListingClientService.getListingStatus();
    }

    @Scheduled(fixedDelay = 5000) // раз в 5 секунд
    public void fetchDataTask() {
        if (initialized) {
            if (symbols != null) {
                symbols.forEach(symbol -> fetchStockQuotesExecutorService.submit(() -> {
                    try {
                        ResponseEntity<String> responseEntity = nyseGet.getGlobalQuote(symbol, apiKey);
                        if (responseEntity.getStatusCode().is2xxSuccessful()) {
                            String response = responseEntity.getBody();
                            processStockQuotesExecutorService.submit(() -> {
                                try {
                                    processData(response);
                                } catch (JsonProcessingException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                        } else {
                            // Обработка ошибки
                            System.out.println("Ошибка получения данных: " + responseEntity.getStatusCode());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }));
            }
        }
    }

    public void processData(String response) throws JsonProcessingException {
        if(!(response == null)) {
            SymbolDTO symbolDTO = processCurrentRateResponse.processData(response);
            symbolRepo.save(SymbolMapper.symbolMapper.toEntity(symbolDTO));
            log.info("Saving stock quote with name " + symbolDTO.getSymbol());
        }
    }
}
