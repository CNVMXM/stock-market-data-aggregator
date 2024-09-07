package com.cnvmxm.aggregatorservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "alpha-vantage", url = "https://www.alphavantage.co")
public interface NYSEGet {
    //параметр produces = "application/json" указываает на тип возвращаемого значения, который
    //будет храниться в response
//    @GetMapping(value = "/query?function=LISTING_STATUS&apikey={apiKey}")
//    ResponseEntity<Resource> fetchListingStatus(@PathVariable("apiKey") String apiKey);
//
//    @GetMapping(value = "/query?function=GLOBAL_QUOTE&symbol={symbol}&apikey={apiKey}")
//    ResponseEntity<String> fetchGlobalQuote(@PathVariable("symbol") String symbol, @PathVariable("apiKey") String apiKey);

}
