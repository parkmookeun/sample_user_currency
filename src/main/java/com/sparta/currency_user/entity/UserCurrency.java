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

    @ManyToOne
    @Column(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "to_currency_id")
    private Currency currency;

    @Column(name = "amount_in_krw", nullable = false)
    private Long fromAmount;

    @Column(name = "amount_after_exchange", nullable = false)
    private Long toAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Builder
    public UserCurrency(User user, Currency currency, Long fromAmount,
                        Long toAmount, Status status){
        this.user = user;
        this.currency = currency;
        this.fromAmount = fromAmount;
        this.toAmount = toAmount;
        this.status = status;
    }
}
