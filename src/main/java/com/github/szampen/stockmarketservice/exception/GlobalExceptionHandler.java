package com.github.szampen.stockmarketservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StockNotFoundException.class)
    public ResponseEntity<Object> handleStockNotFound(StockNotFoundException exception){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", exception.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND); // 404
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<Object> handleInsufficientStock(InsufficientStockException exception){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", exception.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST); // 400
    }

}
