package com.cnvmxm.aggregatorservice.util;

import com.cnvmxm.aggregatorservice.model.dto.QuoteDTO;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.RFC4180ParserBuilder;
import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ProcessCurrentListingQuote {

    public List<QuoteDTO> ProcessListing(String csvData) {
        StringReader stringReader = new StringReader(csvData);
        CSVReader csvReader = new CSVReaderBuilder(stringReader)
                .withCSVParser(new RFC4180ParserBuilder().build())
                .build();

        try {
            List<QuoteDTO> quotes = new ArrayList<>();
            String[] nextLine;
            // Пропускаем заголовок
            csvReader.readNext();
            while ((nextLine = csvReader.readNext()) != null) {
                QuoteDTO quote = new QuoteDTO();
                quote.setSymbol(nextLine[0]);
                quote.setName(nextLine[1]);
                quote.setExchange(nextLine[2]);
                quote.setAssetType(nextLine[3]);
                quote.setIpoDate(nextLine[4]);
                quote.setDelistingDate(nextLine[5]);
                quote.setStatus(nextLine[6]);
                quotes.add(quote);
            }
            // Дальнейшая обработка списка QuoteDTO
            log.info("Получено " + quotes.size() + quotes.size() + " записей");
            return quotes;
        } catch (IOException | CsvValidationException e) {
            // Обработка ошибки
            System.out.println("Ошибка парсинга CSV: " + e.getMessage());
            return null;
        }
    }
}
