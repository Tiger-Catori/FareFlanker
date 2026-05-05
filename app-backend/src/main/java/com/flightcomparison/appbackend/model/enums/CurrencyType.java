package com.flightcomparison.appbackend.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CurrencyType {
    USD, EUR, GBP;
    @JsonValue
    public String getCode() {
        return name();
    }

    @JsonCreator
    public static CurrencyType fromCode(String code) {
        return valueOf(code.toUpperCase());
    }


}
