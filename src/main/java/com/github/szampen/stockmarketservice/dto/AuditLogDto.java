package com.github.szampen.stockmarketservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.szampen.stockmarketservice.entity.ActionType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuditLogDto {
    private ActionType type;

    @JsonProperty("wallet_id")
    private String walletId;

    @JsonProperty("stock_name")
    private String stockName;
}
