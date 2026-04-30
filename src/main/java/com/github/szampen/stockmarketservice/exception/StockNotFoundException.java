package com.github.szampen.stockmarketservice.exception;

// Extends RuntimeException, so spring automatically rollbacks the transaction, if stock doesn't exist
public class StockNotFoundException extends RuntimeException{
    public StockNotFoundException(String message){
        super(message);
    }
}
