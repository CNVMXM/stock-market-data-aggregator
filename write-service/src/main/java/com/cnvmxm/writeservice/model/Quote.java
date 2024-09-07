package com.cnvmxm.writeservice.model;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@ToString
@Data
@FieldDefaults(level = PRIVATE)
public class Quote {

    Long id;

    String symbol;

    String name;

    String exchange;


    String assetType;


    String ipoDate;


    String delistingDate;

    String status;

}
