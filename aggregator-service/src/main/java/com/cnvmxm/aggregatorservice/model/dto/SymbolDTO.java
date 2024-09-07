package com.cnvmxm.aggregatorservice.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class SymbolDTO {

    @NotNull
    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("open")
    private BigDecimal open;

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
