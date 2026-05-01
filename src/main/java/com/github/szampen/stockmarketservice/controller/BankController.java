package com.github.szampen.stockmarketservice.controller;

import com.github.szampen.stockmarketservice.dto.BankStateRequest;
import com.github.szampen.stockmarketservice.dto.StockDto;
import com.github.szampen.stockmarketservice.entity.BankStock;
import com.github.szampen.stockmarketservice.service.BankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stocks")
public class BankController {
    private final BankService bankService;

    public BankController(BankService service){
        this.bankService = service;
    }

    @GetMapping
    public ResponseEntity<Map<String,Object>> getBankState(){
        List<StockDto> stocks = bankService.getBankState().stream()
                .map(s -> new StockDto(s.getSymbol(), s.getQuantity()))
                .toList();
        return ResponseEntity.ok(Map.of("stocks", stocks));
    }

    @PostMapping
    public ResponseEntity<Void> setBankState(@RequestBody BankStateRequest request){
        List<BankStock> newStocks = request.getStocks().stream()
                .map(dto -> {
                    BankStock stock = new BankStock();
                    stock.setSymbol(dto.getName());
                    stock.setQuantity(dto.getQuantity());
                    return stock;
                })
                .toList();

        bankService.setBankState(newStocks);
        return ResponseEntity.ok().build();
    }
}
