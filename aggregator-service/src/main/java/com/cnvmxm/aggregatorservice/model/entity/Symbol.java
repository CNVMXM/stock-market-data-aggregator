package com.cnvmxm.aggregatorservice.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Data
@FieldDefaults(level = PRIVATE)
public class Symbol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String symbol;

    BigDecimal open;

    BigDecimal high;

    BigDecimal low;

    BigDecimal price;

    BigDecimal volume;

    String latestTradingDay;

    BigDecimal previousClose;

    BigDecimal change;

    String changePercent;
}
