package com.cnvmxm.aggregatorservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "alpha-vantage", url = "https://www.alphavantage.co")
public interface NYSEGet {

    @GetMapping(value = "/query?function=LISTING_STATUS&apikey={apiKey}", produces = "text/csv")
    ResponseEntity<String> fetchListingStatus(@PathVariable("apiKey") String apiKey);

    @GetMapping(value = "/query?function=GLOBAL_QUOTE&symbol={symbol}&apikey={apiKey}", produces = "application/json")
    ResponseEntity<String> getGlobalQuote(@PathVariable("symbol") String symbol, @PathVariable("apiKey") String apiKey);

}
