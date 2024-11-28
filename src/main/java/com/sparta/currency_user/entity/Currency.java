package com.sparta.currency_user.entity;

import com.sparta.currency_user.entity.enums.CurrencyCode;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Getter
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency_code", nullable = false, unique = true)
    private CurrencyCode currencyCode;

    @Column(name = "exchange_rate", nullable = false)
    private BigDecimal exchangeRate;

    private String symbol;

    public Currency(CurrencyCode currencyCode, BigDecimal exchangeRate, String symbol) {
        this.currencyCode = currencyCode;
        this.exchangeRate = exchangeRate;
        this.symbol = symbol;
    }

    public Currency() {}
}
