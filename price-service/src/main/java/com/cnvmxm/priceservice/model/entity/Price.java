package com.cnvmxm.priceservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stockName;
    private BigDecimal stockValue;

    //id генерируется Jpa автоматически
    public Price(String symbol, BigDecimal price) {
        this.stockName = symbol;
        this.stockValue = price;
    }
}
