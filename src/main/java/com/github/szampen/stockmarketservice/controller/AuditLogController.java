package com.github.szampen.stockmarketservice.controller;

import com.github.szampen.stockmarketservice.service.AuditLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/log")
public class AuditLogController {
    private final AuditLogService auditLogService;

    public AuditLogController(AuditLogService service){
        this.auditLogService = service;
    }

    @GetMapping
    public ResponseEntity<Map<String,Object>> getAllLogs(){
        return ResponseEntity.ok(Map.of("log", auditLogService.getAllLogs()));
    }
}
