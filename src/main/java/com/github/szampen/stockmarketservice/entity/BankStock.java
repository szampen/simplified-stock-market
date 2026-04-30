package com.github.szampen.stockmarketservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bank_stocks")
@Data // getters, setters, toString
@NoArgsConstructor
@AllArgsConstructor
@Builder // builder design pattern
public class BankStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String symbol;

    @Column(nullable = false)
    private int quantity;

}
