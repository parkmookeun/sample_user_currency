package com.sparta.currency_user.dto;

// 환전 요청 dto
// 고객은 있으니까, 화폐 이름, 환전 전 금액

import com.sparta.currency_user.entity.enums.CurrencyCode;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CreateExchangeReqDto {

    private CurrencyCode currencyCode;
    private BigDecimal money;

    public CreateExchangeReqDto(CurrencyCode currencyCode, BigDecimal money){
        this.currencyCode = currencyCode;
        this.money = money;
    }
}
