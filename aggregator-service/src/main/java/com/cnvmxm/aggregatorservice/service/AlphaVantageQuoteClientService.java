package com.cnvmxm.aggregatorservice.service;

import com.cnvmxm.aggregatorservice.client.NYSEGet;
import com.cnvmxm.aggregatorservice.model.entity.Symbol;
import com.cnvmxm.aggregatorservice.repository.QuoteRepository;
import com.cnvmxm.aggregatorservice.repository.SymbolRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class AlphaVantageQuoteClientService {

    @Value("${nyse.personal.api-key}")
    private String apiKey;

    private List<String> symbols;
    private boolean initialized = false;

//    private final ExecutorService fetchStockQuotesExecutorService;
//    private final ExecutorService processStockQuotesExecutorService;processStockQuotesExecutorService
    private final NYSEGet nyseGet;
    private final SymbolRepository symbolRepo;
    private final QuoteRepository quoteRepo;
    private final AlphaVantageListingClientService alphaVantageListingClientService;

    public AlphaVantageQuoteClientService(
            NYSEGet nyseGet,
            SymbolRepository symbolRepo,
            QuoteRepository quoteRepo,
            AlphaVantageListingClientService alphaVantageListingClientService
    ) {
//        fetchStockQuotesExecutorService = Executors.newFixedThreadPool(2);
//        processStockQuotesExecutorService = Executors.newFixedThreadPool(2);
        this.nyseGet = nyseGet;
        this.symbolRepo = symbolRepo;
        this.quoteRepo = quoteRepo;
        this.alphaVantageListingClientService = alphaVantageListingClientService;
    }


    @Scheduled(initialDelay = 1000, fixedDelay = Long.MAX_VALUE)
    public void startPolling() {
        updateListingStatus();
        //updateSymbols();
        //initialized = true;
    }
    //batching
    //transOutbox в портфолио

    @Scheduled(cron = "0 */30 * * * *")
    public void updateListingStatus() {
        try {
            alphaVantageListingClientService.getListingStatus();
        } catch (Exception e) {
            System.err.println("Ошибка при получении статуса листинга: " + e.getMessage());
        }
    }

    @Scheduled(cron = "0 */35 * * * *")
    public void updateSymbols() {
        try {
            symbols = quoteRepo.findAllSymbols();
            System.out.println(symbols);
        } catch (Exception e) {
            // Логируем ошибку
            System.err.println("Ошибка при обновлении символов: " + e.getMessage());
        }
    }

    @Scheduled(fixedDelay = 5000) // раз в 5 секунд
    public void fetchDataTask() {
        if (initialized) {
            if (symbols != null) {
                //try (ForkJoinPool forkJoinPool = new ForkJoinPool()) {
                    //forkJoinPool.submit(() -> symbols.parallelStream().forEach(symbol -> {
                for(String symbol : symbols) {
                        ResponseEntity<String> responseEntity = nyseGet.fetchGlobalQuote(symbol, apiKey);
                        if (responseEntity.getStatusCode().is2xxSuccessful()) {
                            String response = responseEntity.getBody();
                            log.info("Fetch Quote response: " + response);
                            try {
                                Symbol symbolEntity = parseResponse(response);
                                symbolRepo.save(symbolEntity);
                            } catch (JsonProcessingException e) {
                                // Обработка ошибки
                                System.out.println("Ошибка парсинга JSON: " + e.getMessage());
                            }
                        } else {
                            // Обработка ошибки
                            System.out.println("Ошибка получения данных: " + responseEntity.getStatusCode());
                        }

//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                }
            }
        }
    }



    public Symbol parseResponse(String response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        // Чтение JSON как JsonNode для более гибкой обработки
        JsonNode rootNode = mapper.readTree(response);
        JsonNode globalQuoteNode = rootNode.path("Global Quote");

        // Извлечение данных из узла "Global Quote"
        String symbolValue = globalQuoteNode.path("01. symbol").asText();
        BigDecimal open = new BigDecimal(globalQuoteNode.path("02. open").asText());
        BigDecimal high = new BigDecimal(globalQuoteNode.path("03. high").asText());
        BigDecimal low = new BigDecimal(globalQuoteNode.path("04. low").asText());
        BigDecimal price = new BigDecimal(globalQuoteNode.path("05. price").asText());
        BigDecimal volume = new BigDecimal(globalQuoteNode.path("06. volume").asText());
        String latestTradingDay = globalQuoteNode.path("07. latest trading day").asText();
        BigDecimal previousClose = new BigDecimal(globalQuoteNode.path("08. previous close").asText());
        BigDecimal change = new BigDecimal(globalQuoteNode.path("09. change").asText());
        String changePercent = globalQuoteNode.path("10. change percent").asText();

        // Создание экземпляра Symbol, заполнив его данными
        Symbol symbolEntity = new Symbol();
        symbolEntity.setSymbol(symbolValue);
        symbolEntity.setOpen(open);
        symbolEntity.setHigh(high);
        symbolEntity.setLow(low);
        symbolEntity.setPrice(price);
        symbolEntity.setVolume(volume);
        symbolEntity.setLatestTradingDay(latestTradingDay);
        symbolEntity.setPreviousClose(previousClose);
        symbolEntity.setChange(change);
        symbolEntity.setChangePercent(changePercent);

        log.info("Mapping stock quote with name " + symbolValue);

        return symbolEntity;
    }
}
