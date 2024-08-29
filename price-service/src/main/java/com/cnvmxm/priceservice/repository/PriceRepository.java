package com.cnvmxm.priceservice.repository;

import com.cnvmxm.priceservice.model.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    List<Price> findByStockNameIn(List<String> values);
}
