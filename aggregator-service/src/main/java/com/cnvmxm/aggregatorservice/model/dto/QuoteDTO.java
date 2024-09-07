package com.cnvmxm.aggregatorservice.model.dto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuoteDTO {

    @NotNull
    private String symbol;

    @NotNull
    private String name;

    @NotNull
    private String exchange;

    @NotNull
    private String assetType;

    @NotNull
    private String ipoDate;

    private String delistingDate;

    @NotNull
    private String status;

}
