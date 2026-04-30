package com.github.szampen.stockmarketservice.exception;

public class InsufficientStockException extends RuntimeException{
    public InsufficientStockException(String message){
        super(message);
    }
}
