package com.cnvmxm.aggregatorservice.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Symbol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String symbol;

    @Column
    private BigDecimal high;

    @Column
    private BigDecimal low;

    @Column
    private BigDecimal price;

    @Column
    private BigDecimal volume;

    @Column
    private String latestTradingDay;

    @Column
    private BigDecimal previousClose;

    @Column
    private BigDecimal change;

    @Column
    private String changePercent;
}
