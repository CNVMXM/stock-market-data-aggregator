package com.cnvmxm.aggregatorservice.model.dto;
import lombok.Data;

@Data
public class QuoteDTO {


    private String symbol;
    private String name;
    private String exchange;
    private String assetType;
    private String ipoDate;
    private String delistingDate;
    private String status;

}
