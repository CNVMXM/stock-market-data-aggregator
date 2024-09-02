package com.cnvmxm.priceservice.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SymbolDTO {
    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("high")
    private BigDecimal high;

    @JsonProperty("low")
    private BigDecimal low;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("volume")
    private BigDecimal volume;

    @JsonProperty("latestTradingDay")
    private String latestTradingDay;

    @JsonProperty("previousClose")
    private BigDecimal previousClose;

    @JsonProperty("change")
    private BigDecimal change;

    @JsonProperty("changePercent")
    private String changePercent;

    @Override
    public String toString() {
        return "SymbolDTO{" +
                "symbol='" + symbol + '\'' +
                ", high=" + high +
                ", low=" + low +
                ", price=" + price +
                ", volume=" + volume +
                ", latestTradingDay=" + latestTradingDay +
                ", previousClose=" + previousClose +
                ", change=" + change +
                ", changePercent=" + changePercent +
                '}';
    }
}
