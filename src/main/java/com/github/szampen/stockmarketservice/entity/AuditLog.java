package com.github.szampen.stockmarketservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Data
@NoArgsConstructor
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String walletId;

    @Enumerated(EnumType.STRING) // Enum is saved as String instead of int (0/1)
    @Column(nullable = false)
    private ActionType action;

    @Column(nullable = false)
    private String symbol;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public AuditLog(String walletId, String symbol, int quantity, ActionType action){
        this.walletId = walletId;
        this.symbol = symbol;
        this.quantity = quantity;
        this.action = action;
    }
}
