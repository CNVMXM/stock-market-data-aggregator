package com.cnvmxm.aggregatorservice.util;

import com.cnvmxm.aggregatorservice.model.dto.QuoteDTO;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.RFC4180ParserBuilder;
import com.opencsv.exceptions.CsvValidationException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvParseUtil {

    public static CSVReader createCsvReader(File file) throws IOException {
        return new CSVReaderBuilder(new FileReader(file))
                .withCSVParser(new RFC4180ParserBuilder().build())
                .build();
    }

    public static List<QuoteDTO> parseQuotesFromCsv(CSVReader csvReader) throws CsvValidationException, IOException {
        List<QuoteDTO> quoteDTOList = new ArrayList<>();
        String[] values;

        while ((values = csvReader.readNext()) != null) {
            QuoteDTO quoteDTO = new QuoteDTO();
            quoteDTO.setSymbol(values[0]);
            quoteDTO.setName(values[1]);
            quoteDTO.setExchange(values[2]);
            quoteDTO.setAssetType(values[3]);
            quoteDTO.setIpoDate(values[4]);
            quoteDTO.setDelistingDate(values[5]);
            quoteDTO.setStatus(values[6]);

            quoteDTOList.add(quoteDTO);
        }
        return quoteDTOList;
    }
}
