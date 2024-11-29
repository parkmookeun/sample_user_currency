package com.sparta.currency_user.dto;

import com.sparta.currency_user.entity.Currency;
import com.sparta.currency_user.entity.enums.CurrencyCode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CurrencyRequestDto {
    @NotBlank
    private CurrencyCode currencyCode;

    @NotBlank
    @Positive
    private BigDecimal exchangeRate;

    @NotBlank
    private String symbol;

    public Currency toEntity() {
        return new Currency(
                this.currencyCode,
                this.exchangeRate,
                this.symbol
        );
    }
}
