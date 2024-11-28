package com.sparta.currency_user.repository;

import com.sparta.currency_user.dto.UserTotalInfo;
import com.sparta.currency_user.entity.User;
import com.sparta.currency_user.entity.UserCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExchangeRepository extends JpaRepository<UserCurrency, Long> {
    List<UserCurrency> findAllByUser(User user);

    @Query("select new com.sparta.currency_user.dto.UserTotalInfo(COUNT(c.id), SUM(c.fromAmount)) from UserCurrency c WHERE c.user.id = :userId group by c.currency.currencyName")
    UserTotalInfo findTotalAmountAndCount(@Param("userId") Long userId);
}
