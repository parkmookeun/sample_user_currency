package com.sparta.currency_user.dto;

// 환전 요청 dto
// 고객은 있으니까, 화폐 이름, 환전 전 금액

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CreateExchangeReqDto {

    private String currencyName;
    private BigDecimal money;

    public CreateExchangeReqDto(String currencyName, BigDecimal money){
        this.currencyName = currencyName;
        this.money = money;
    }
}
