package com.cnvmxm.aggregatorservice.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@ToString
@Entity
@Data
@FieldDefaults(level = PRIVATE)
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String symbol;

    String name;

    String exchange;


    String assetType;


    String ipoDate;


    String delistingDate;

    String status;

}
