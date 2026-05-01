package com.github.szampen.stockmarketservice.dto;

import com.github.szampen.stockmarketservice.entity.ActionType;
import lombok.Data;

@Data
public class TradeRequest {
    private ActionType type;
}
