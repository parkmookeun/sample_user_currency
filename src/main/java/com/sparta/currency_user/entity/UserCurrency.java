package com.sparta.currency_user.entity;

import com.sparta.currency_user.entity.base.BaseEntity;
import com.sparta.currency_user.entity.enums.Status;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
@Table(
        name = "user_currency"
)
public class UserCurrency extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "to_currency_id")
    private Currency currency;

    @Column(name = "amount_in_krw", nullable = false)
    private BigDecimal fromAmount;

    @Column(name = "amount_after_exchange", nullable = false)
    private BigDecimal toAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Builder
    public UserCurrency(User user, Currency currency, BigDecimal fromAmount,
                        BigDecimal toAmount, Status status){
        this.user = user;
        this.currency = currency;
        this.fromAmount = fromAmount;
        this.toAmount = toAmount;
        this.status = status;
    }

    public void updateStatus(Status status){
        this.status =status;
    }
}
