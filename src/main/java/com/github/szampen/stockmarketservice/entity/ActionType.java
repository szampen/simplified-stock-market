package com.github.szampen.stockmarketservice.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ActionType {
    BUY, SELL;

    // loading from db - helper that enables lower-case saving into db for jackson
    @JsonCreator
    public static ActionType fromValue(String value){
        return ActionType.valueOf(value);
    }

    // saving in lower-case to db
    @JsonValue
    public String toValue(){
        return name().toLowerCase();
    }
}
