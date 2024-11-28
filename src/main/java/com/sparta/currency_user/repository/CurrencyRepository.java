package com.sparta.currency_user.repository;

import com.sparta.currency_user.entity.Currency;
import com.sparta.currency_user.entity.enums.CurrencyCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Optional<Currency> findByCurrencyCode(CurrencyCode code);
}
