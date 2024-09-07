package com.cnvmxm.aggregatorservice.service;

import com.cnvmxm.aggregatorservice.client.NYSEGet;
import com.cnvmxm.aggregatorservice.mapper.QuoteMapper;
import com.cnvmxm.aggregatorservice.mapper.SymbolMapper;
import com.cnvmxm.aggregatorservice.model.dto.QuoteDTO;
import com.cnvmxm.aggregatorservice.model.dto.SymbolDTO;
import com.cnvmxm.aggregatorservice.model.entity.Quote;
import com.cnvmxm.aggregatorservice.model.entity.Symbol;
import com.cnvmxm.aggregatorservice.repository.QuoteRepository;
import com.cnvmxm.aggregatorservice.repository.SymbolRepository;
import com.cnvmxm.aggregatorservice.util.ProcessCurrentRateResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

@Slf4j
@Service
public class AlphaVantageQuoteClientService {

    @Value("${nyse.api-key}")
    private String apiKey;

    private List<String> symbols;
    private boolean initialized = false;

//    private final ExecutorService fetchStockQuotesExecutorService;
//    private final ExecutorService processStockQuotesExecutorService;processStockQuotesExecutorService
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
//        fetchStockQuotesExecutorService = Executors.newFixedThreadPool(2);
//        processStockQuotesExecutorService = Executors.newFixedThreadPool(2);
        this.nyseGet = nyseGet;
        this.processCurrentRateResponse = processCurrentRateResponse;
        this.symbolRepo = symbolRepo;
        this.quoteRepo = quoteRepo;
        this.alphaVantageListingClientService = alphaVantageListingClientService;
    }


    @Scheduled(initialDelay = 1000, fixedDelay = Long.MAX_VALUE)
    public void startPolling() {
        updateListingStatus();
        log.info("Done: updateListingStatus()");
        //updateSymbols();
        initialized = true;
    }
    //batching
    //transOutbox в портфолио

    @Scheduled(cron = "0 */30 * * * *")
    public void updateListingStatus() {
        try {
            File csvFile = alphaVantageListingClientService.downloadCsv();
            List<QuoteDTO> quotes = alphaVantageListingClientService.parseCsv(csvFile);
            quoteRepo.saveAll(QuoteMapper.quoteMapper.toEntityList(quotes)); // Сохраняем все символы в базе данных
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //@Scheduled(cron = "0 */10 * * * *") // раз в 10 минут
//    @Scheduled(fixedDelay = 1000000)
//    public void updateSymbols() {
//        try {
//            symbols = quoteRepo.findAllSymbols();
//        } catch (Exception e) {
//            // Логируем ошибку
//            System.err.println("Ошибка при обновлении символов: " + e.getMessage());
//        }
//    }

//    @Scheduled(cron = "0 */5 * * * *") // раз в 5 минут
//    public void updateListingStatus() {
//        try {
//            alphaVantageListingClientService.getListingStatus();
//        } catch (Exception e) {
//            // Логируем ошибку
//            System.err.println("Ошибка при получении статуса листинга: " + e.getMessage());
//        }
//    }

//    @Scheduled(fixedDelay = 5000) // раз в 5 секунд
//    public void fetchDataTask() {
//        if (initialized) {
//            if (symbols != null) {
//                try (ForkJoinPool forkJoinPool = new ForkJoinPool()) {
//                    forkJoinPool.submit(() -> symbols.parallelStream().forEach(symbol -> {
//                        ResponseEntity<String> responseEntity = nyseGet.fetchGlobalQuote(symbol, apiKey);
//                        if (responseEntity.getStatusCode().is2xxSuccessful()) {
//                            String response = responseEntity.getBody();
//                            try {
//                                Symbol symbolEntity = parseResponse(response);
//                                symbolRepo.save(symbolEntity);
//                            } catch (JsonProcessingException e) {
//                                // Обработка ошибки
//                                System.out.println("Ошибка парсинга JSON: " + e.getMessage());
//                            }
//                        } else {
//                            // Обработка ошибки
//                            System.out.println("Ошибка получения данных: " + responseEntity.getStatusCode());
//                        }
//                    }));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    private Symbol parseResponse(String response) throws JsonProcessingException {
//
//        ObjectMapper mapper = new ObjectMapper();
//        SymbolDTO symbolDTO = mapper.readValue(response, SymbolDTO.class);
//        log.info("Mapping stock quote with name " + symbolDTO.getSymbol());
//        return SymbolMapper.symbolMapper.toEntity(symbolDTO);
//    }
}
