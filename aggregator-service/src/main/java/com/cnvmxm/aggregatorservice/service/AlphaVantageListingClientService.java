package com.cnvmxm.aggregatorservice.service;

import com.cnvmxm.aggregatorservice.client.NYSEGet;
import com.cnvmxm.aggregatorservice.mapper.QuoteMapper;
import com.cnvmxm.aggregatorservice.model.dto.QuoteDTO;
import com.cnvmxm.aggregatorservice.repository.QuoteRepository;
import com.cnvmxm.aggregatorservice.util.ProcessCurrentListingQuote;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class AlphaVantageListingClientService {

    private final NYSEGet nyseGet;
    private final QuoteRepository quoteRepository;
    private final ProcessCurrentListingQuote processCurrentListingQuote;

    @Value("${nyse.api-key}")
    private String apiKey;

    public void getListingStatus() {
        ResponseEntity<String> responseEntity = nyseGet.fetchListingStatus(apiKey);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String csvData = responseEntity.getBody();
            List<QuoteDTO> quoteDTOList = processCurrentListingQuote.ProcessListing(csvData);
            if(quoteDTOList != null) {
                saveCsvDataToDatabase(quoteDTOList);
            }
        } else {
            // Обработка ошибки
            System.out.println("Ошибка получения данных: " + responseEntity.getStatusCode());
        }
    }


    public void saveCsvDataToDatabase(List<QuoteDTO> quoteDTOList) {
        // Логика сохранения данных CSV в базу данных

        for(QuoteDTO quoteDTO : quoteDTOList) {
            quoteRepository.save(QuoteMapper.quoteMapper.toEntity(quoteDTO));
        }
    }
}
