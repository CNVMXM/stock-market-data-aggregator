package com.cnvmxm.portfolioservice.repository;

import com.cnvmxm.portfolioservice.model.entity.Client;
import com.cnvmxm.portfolioservice.model.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("""
        SELECT st FROM Client cl JOIN cl.stocks
         st WHERE cl.clientId = :clientId
        """)
    List<Stock> findStocksByClientId(@Param("clientId") Long clientId);
}
