package com.github.szampen.stockmarketservice.service;

import com.github.szampen.stockmarketservice.entity.BankStock;
import com.github.szampen.stockmarketservice.repository.BankStockRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {
    private final BankStockRepository bankRepository;

    public BankService(BankStockRepository bankStockRepository){
        this.bankRepository = bankStockRepository;
    }

    public List<BankStock> getBankState(){
        return bankRepository.findAll();
    }

    @Transactional
    public void setBankState(List<BankStock> newStocks){
        bankRepository.deleteAll();
        bankRepository.saveAll(newStocks);
    }

}
