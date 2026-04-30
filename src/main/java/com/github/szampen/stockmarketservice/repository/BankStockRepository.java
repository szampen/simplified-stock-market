package com.github.szampen.stockmarketservice.repository;

import com.github.szampen.stockmarketservice.entity.BankStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankStockRepository extends JpaRepository<BankStock,Long> {
    Optional<BankStock> findBySymbol(String symbol);
}
