package com.github.szampen.stockmarketservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "wallet_stocks",
        uniqueConstraints = @UniqueConstraint(columnNames = {"walletId", "symbol"})
)
@Data
@NoArgsConstructor
public class WalletStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String walletId;

    @Column(nullable = false)
    private String symbol;

    @Column(nullable = false)
    private int quantity;

    public WalletStock(String walletId, String symbol, int quantity){
        this.walletId = walletId;
        this.symbol = symbol;
        this.quantity = quantity;
    }
}
