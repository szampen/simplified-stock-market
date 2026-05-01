package com.github.szampen.stockmarketservice.controller;

import com.github.szampen.stockmarketservice.dto.TradeRequest;
import com.github.szampen.stockmarketservice.entity.WalletStock;
import com.github.szampen.stockmarketservice.repository.WalletStockRepository;
import com.github.szampen.stockmarketservice.service.TradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wallets")
public class WalletController {
    private final TradeService tradeService;
    private final WalletStockRepository walletRepo;

    public WalletController(TradeService service, WalletStockRepository repo){
        this.tradeService = service;
        this.walletRepo = repo;
    }

    @PostMapping("/{wallet_id}/stocks/{stock_name}")
    public ResponseEntity<Void> executeTrade(
            @PathVariable String wallet_id,
            @PathVariable String stock_name,
            @RequestBody TradeRequest request
    ){
        tradeService.executeTrade(wallet_id, stock_name, request.getType());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{wallet_id}")
    public ResponseEntity<Map<String, Object>> getWallet(@PathVariable String wallet_id){
        List<WalletStock> stocks = walletRepo.findAllByWalletId(wallet_id);

        return ResponseEntity.ok(Map.of(
                "id", wallet_id,
                "stocks", stocks.stream().map(s -> Map.of(
                        "name", s.getSymbol(),
                        "quantity", s.getQuantity()
                ))
        ));
    }

    @GetMapping("/{wallet_id}/stocks/{stock_name}")
    public ResponseEntity<Integer> getStockQuantity(@PathVariable String wallet_id, @PathVariable String stock_name){
        int quantity = walletRepo.findByWalletIdAndSymbol(wallet_id,stock_name)
                .map(WalletStock::getQuantity)
                .orElse(0);

        return ResponseEntity.ok(quantity);
    }
}
