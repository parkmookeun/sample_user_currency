package com.sparta.currency_user.dto;

import com.sparta.currency_user.entity.UserCurrency;
import com.sparta.currency_user.entity.enums.CurrencyCode;
import com.sparta.currency_user.entity.enums.Status;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//환전 요청을 성공시, 반환에 필요한 정보
//고객 이름, 통화 이름, 환전 전 금액, 환전 후 금액, 상태, 생성일자
@Getter
public class CreateExchangeResDto {
    private String name;
    private CurrencyCode currencyCode;
    private BigDecimal beforeExchange;
    private BigDecimal afterExchange;
    private Status status;
    private LocalDateTime createdAt;

    public CreateExchangeResDto(UserCurrency userCurrency){
        this.name = userCurrency.getUser().getName();
        this.currencyCode = userCurrency.getCurrency().getCurrencyCode();
        this.beforeExchange = userCurrency.getFromAmount();
        this.afterExchange = userCurrency.getToAmount();
        this.status = userCurrency.getStatus();
        this.createdAt = userCurrency.getCreatedAt();
    }
}
