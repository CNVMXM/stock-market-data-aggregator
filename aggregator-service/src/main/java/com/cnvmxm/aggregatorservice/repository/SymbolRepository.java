package com.cnvmxm.aggregatorservice.repository;

import com.cnvmxm.aggregatorservice.model.entity.Symbol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SymbolRepository extends JpaRepository<Symbol, Long> {
}
