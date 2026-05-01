package com.github.szampen.stockmarketservice.service;

import com.github.szampen.stockmarketservice.entity.ActionType;
import com.github.szampen.stockmarketservice.entity.AuditLog;
import com.github.szampen.stockmarketservice.repository.AuditLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditLogService {
    private final AuditLogRepository auditLogRepository;

    public AuditLogService(AuditLogRepository auditLogRepository){
        this.auditLogRepository = auditLogRepository;
    }

    public void log(String walletId, String symbol, int quantity, ActionType type){
        AuditLog entry = new AuditLog(walletId, symbol, quantity, type);
        auditLogRepository.save(entry);
    }

    public List<AuditLog> getAllLogs(){
        return auditLogRepository.findAllByOrderByCreatedAtAsc();
    }
}
