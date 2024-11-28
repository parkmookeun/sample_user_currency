package com.sparta.currency_user.dto;

import com.sparta.currency_user.entity.Currency;
import com.sparta.currency_user.entity.enums.CurrencyCode;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CurrencyResponseDto {
    private Long id;
    private CurrencyCode currencyCode;
    private BigDecimal exchangeRate;
    private String symbol;

    public CurrencyResponseDto(Currency currency) {
        this.id = currency.getId();
        this.currencyCode = currency.getCurrencyCode();
        this.exchangeRate = currency.getExchangeRate();
        this.symbol = currency.getSymbol();
    }

    public CurrencyResponseDto(Long id, CurrencyCode currencyCode, BigDecimal exchangeRate, String symbol) {
        this.id = id;
        this.currencyCode = currencyCode;
        this.exchangeRate = exchangeRate;
        this.symbol = symbol;
    }

    public static CurrencyResponseDto toDto(Currency currency) {
        return new CurrencyResponseDto(
            currency.getId(),
            currency.getCurrencyCode(),
            currency.getExchangeRate(),
            currency.getSymbol()
        );
    }
}
