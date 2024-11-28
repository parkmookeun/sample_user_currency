package com.sparta.currency_user.dto;

import com.sparta.currency_user.entity.Currency;
import com.sparta.currency_user.entity.enums.CurrencyCode;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CurrencyRequestDto {
    private CurrencyCode currencyCode;
    private BigDecimal exchangeRate;
    private String symbol;

    public Currency toEntity() {
        return new Currency(
                this.currencyCode,
                this.exchangeRate,
                this.symbol
        );
    }
}
