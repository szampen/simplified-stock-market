package com.github.szampen.stockmarketservice.repository;


import com.github.szampen.stockmarketservice.entity.WalletStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletStockRepository extends JpaRepository<WalletStock, Long> {
    List<WalletStock> findAllByWalletId(String walletId);

    // Optional because it can be null
    Optional<WalletStock> findByWalletIdAndSymbol(String walletId, String symbol);
}
