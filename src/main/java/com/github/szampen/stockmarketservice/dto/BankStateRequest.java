package com.github.szampen.stockmarketservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class BankStateRequest {
    private List<StockDto> stocks;
}
