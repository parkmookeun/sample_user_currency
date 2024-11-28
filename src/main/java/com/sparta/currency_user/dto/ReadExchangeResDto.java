package com.sparta.currency_user.dto;

import com.sparta.currency_user.entity.UserCurrency;
import com.sparta.currency_user.entity.enums.CurrencyCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 환전정보 조회 응답 dto
 *
 */
@Getter
public class ReadExchangeResDto {
    private String name;
    private CurrencyCode currencyCode;
    private BigDecimal beforeAmount;
    private BigDecimal afterAmount;
    private LocalDateTime createdAt;

    public ReadExchangeResDto(UserCurrency userCurrency){
        this.name = userCurrency.getUser().getName();
        this.currencyCode = userCurrency.getCurrency().getCurrencyCode();
        this.beforeAmount = userCurrency.getFromAmount();
        this.afterAmount = userCurrency.getToAmount();
        this.createdAt = userCurrency.getCreatedAt();
    }

}
