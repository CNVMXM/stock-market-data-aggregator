package com.cnvmxm.aggregatorservice.service;

import com.cnvmxm.aggregatorservice.mapper.QuoteMapper;
import com.cnvmxm.aggregatorservice.model.dto.QuoteDTO;
import com.cnvmxm.aggregatorservice.model.entity.Quote;
import com.cnvmxm.aggregatorservice.producer.KafkaQuoteMessagePublisher;
import com.cnvmxm.aggregatorservice.repository.QuoteRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

import static com.cnvmxm.aggregatorservice.util.CsvParseUtil.*;
import static com.cnvmxm.aggregatorservice.util.CsvParseUtil.createCsvReader;
import static com.cnvmxm.aggregatorservice.util.FileUtil.createTempFile;
import static com.cnvmxm.aggregatorservice.util.HttpUtil.downloadFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class AlphaVantageListingClientService {

    private final KafkaQuoteMessagePublisher quoteMessagePublisher;
    private final QuoteRepository quoteRepository;
    private final QuoteMapper quoteMapper;

    @Value("${nyse.api-key}")
    private String apiKey;

    @Value("${nyse.url}")
    private String API_URL;

    public void getListingStatus() throws Exception {
        List<QuoteDTO> quoteDTOList = parseCsv(downloadCsv());
        for (QuoteDTO quoteDTO : quoteDTOList) {
            Quote quote = quoteMapper.toEntity(quoteDTO);
            quoteMessagePublisher.sendMessage(quote);
        }
    }

    public File downloadCsv() throws IOException {
        String url = API_URL + apiKey;

        byte[] fileBytes = downloadFile(url);
        return createTempFile("listings", ".csv", fileBytes);
    }

    public List<QuoteDTO> parseCsv(File file) throws IOException {
        List<QuoteDTO> quoteDTOList;

        try (CSVReader csvReader = createCsvReader(file)) {
            quoteDTOList = parseQuotesFromCsv(csvReader);
        } catch (CsvValidationException e) {
            log.error("Ошибка валидации CSV: ", e);
            throw new RuntimeException(e);
        }
        log.info("Parsed {} symbols.", quoteDTOList.size());
        return quoteDTOList;
    }
}