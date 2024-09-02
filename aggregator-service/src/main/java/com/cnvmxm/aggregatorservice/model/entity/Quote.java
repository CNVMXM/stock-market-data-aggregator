package com.cnvmxm.aggregatorservice.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Quote {

    @Id
    private Long id;

    @Column
    private String symbol;

    @Column
    private String name;

    @Column
    private String exchange;

    @Column
    private String assetType;

    @Column
    private String ipoDate;

    @Column
    private String delistingDate;

    @Column
    private String status;

}
