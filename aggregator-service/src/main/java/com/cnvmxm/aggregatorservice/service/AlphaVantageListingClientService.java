package com.cnvmxm.aggregatorservice.service;

import com.cnvmxm.aggregatorservice.client.NYSEGet;
import com.cnvmxm.aggregatorservice.mapper.QuoteMapper;
import com.cnvmxm.aggregatorservice.model.dto.QuoteDTO;
import com.cnvmxm.aggregatorservice.model.entity.Quote;
import com.cnvmxm.aggregatorservice.repository.QuoteRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.RFC4180ParserBuilder;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class AlphaVantageListingClientService {

    private final NYSEGet nyseGet;
    private final QuoteRepository quoteRepository;

    @Value("${nyse.api-key}")
    private String apiKey;


    public File downloadCsv() throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String API_URL = "https://www.alphavantage.co/query?function=LISTING_STATUS&apikey=";
        String url = API_URL + apiKey;

        try {
            ResponseEntity<byte[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, byte[].class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                byte[] fileBytes = responseEntity.getBody();
                if (fileBytes == null) {
                    throw new IOException("Не удалось загрузить CSV: пустой ответ");
                }
                try {
                    File tempFile = File.createTempFile("listings", ".csv");
                    FileOutputStream fos = new FileOutputStream(tempFile);
                    fos.write(fileBytes);
                    return tempFile;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new IOException("Не удалось загрузить CSV: " + responseEntity.getStatusCode());
            }
        } catch (RestClientException e) {
            throw new IOException("Не удалось загрузить CSV: ошибка сети", e);
        } catch (IOException e) {
            throw new IOException("Не удалось загрузить CSV: ошибка ввода/вывода", e);
        }
    }

    public List<QuoteDTO> parseCsv(File file) throws IOException {
        List<QuoteDTO> quoteDTOList = new ArrayList<>();
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(file))
                .withCSVParser(new RFC4180ParserBuilder().build())
                .build()) {
            String[] values;
            log.info("About to parse");
            while ((values = csvReader.readNext()) != null) {
                QuoteDTO quoteDTO = new QuoteDTO();

                quoteDTO.setSymbol(values[0]);
                quoteDTO.setName(values[1]);
                quoteDTO.setExchange(values[2]);
                quoteDTO.setAssetType(values[3]);
                quoteDTO.setIpoDate(values[4]);
                quoteDTO.setDelistingDate(values[5]);
                quoteDTO.setStatus(values[6]);

                log.info("Parsed quote: {}", quoteDTO);
                quoteDTOList.add(quoteDTO);
            }
        } catch (CsvValidationException e) {
            log.error("Ошибка валидации CSV: ", e);
            throw new RuntimeException(e);
        }

        log.info("Parsed {} symbols.", quoteDTOList.size());

        return quoteDTOList;
    }
}