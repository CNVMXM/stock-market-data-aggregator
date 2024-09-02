package com.cnvmxm.aggregatorservice.repository;

import com.cnvmxm.aggregatorservice.model.entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {
    @Query("SELECT q.symbol FROM Quote q")
    List<String> findAllSymbols();
}
