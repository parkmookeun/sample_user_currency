package com.sparta.currency_user.entity;

import com.sparta.currency_user.entity.enums.Status;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(
        name = "user_currency"
)
public class UserCurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "to_currency_id", nullable = false)
    private Long currencyId;

    @Column(name = "amount_in_krw", nullable = false)
    private Long fromAmount;

    @Column(name = "amount_after_exchange", nullable = false)
    private Long toAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Builder
    public UserCurrency(Long currencyId, Long fromAmount,
                        Long toAmount, Status status){
        this.currencyId = currencyId;
        this.fromAmount = fromAmount;
        this.toAmount = toAmount;
        this.status = status;
    }
}
