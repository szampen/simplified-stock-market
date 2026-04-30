package com.github.szampen.stockmarketservice.service;

import com.github.szampen.stockmarketservice.entity.ActionType;
import com.github.szampen.stockmarketservice.entity.BankStock;
import com.github.szampen.stockmarketservice.entity.WalletStock;
import com.github.szampen.stockmarketservice.exception.InsufficientStockException;
import com.github.szampen.stockmarketservice.exception.StockNotFoundException;
import com.github.szampen.stockmarketservice.repository.BankStockRepository;
import com.github.szampen.stockmarketservice.repository.WalletStockRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TradeService {
    private final BankStockRepository bankRepo;
    private final WalletStockRepository walletRepo;
    private final AuditLogService auditLogService;

    public TradeService(BankStockRepository bank, WalletStockRepository wallet, AuditLogService logService){
        this.bankRepo = bank;
        this.walletRepo = wallet;
        this.auditLogService = logService;
    }

    @Transactional
    public void executeTrade(String walletId, String symbol, ActionType type){
        BankStock currentStock = bankRepo.findBySymbol(symbol)
                .orElseThrow(() -> new StockNotFoundException("Stock " + symbol + " not found."));

        if (type == ActionType.BUY){
            handleBuy(walletId, currentStock);
        } else {
            handleSell(walletId, currentStock);
        }
    }

    public void handleBuy(String walletId, BankStock bankStock){
        if(bankStock.getQuantity() <= 0){
            throw new InsufficientStockException("No stock available in the bank.");
        }

        bankStock.setQuantity(bankStock.getQuantity() - 1);
        bankRepo.save(bankStock);

        WalletStock walletStock = walletRepo.findByWalletIdAndSymbol(walletId, bankStock.getSymbol())
                .orElse(new WalletStock(walletId, bankStock.getSymbol(), 0));

        walletStock.setQuantity(walletStock.getQuantity() + 1);
        walletRepo.save(walletStock);

        auditLogService.log(walletId, bankStock.getSymbol(), 1, ActionType.BUY);
    }

    public void handleSell(String walletId, BankStock bankStock){
        WalletStock walletStock = walletRepo.findByWalletIdAndSymbol(walletId, bankStock.getSymbol())
                .filter(ws -> ws.getQuantity() > 0)
                .orElseThrow(() -> new InsufficientStockException("No stock available in the wallet."));

        walletStock.setQuantity(walletStock.getQuantity() - 1);
        walletRepo.save(walletStock);

        bankStock.setQuantity(bankStock.getQuantity() - 1);
        bankRepo.save(bankStock);

        auditLogService.log(walletId, bankStock.getSymbol(), 1, ActionType.SELL);
    }
}
