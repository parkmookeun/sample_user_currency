package com.sparta.currency_user.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class UserTotalInfo {
    private Long totalCount;
    private BigDecimal totalSum;

    public UserTotalInfo(Long totalCount, BigDecimal totalSum){
        this.totalCount = totalCount;
        this.totalSum = totalSum;
    }
}
