package com.sparta.currency_user.service;

import com.sparta.currency_user.dto.CreateExchangeReqDto;
import com.sparta.currency_user.dto.CreateExchangeResDto;
import com.sparta.currency_user.dto.ReadExchangeResDto;
import com.sparta.currency_user.dto.UserTotalInfo;
import com.sparta.currency_user.entity.Currency;
import com.sparta.currency_user.entity.User;
import com.sparta.currency_user.entity.UserCurrency;
import com.sparta.currency_user.entity.enums.CurrencyCode;
import com.sparta.currency_user.entity.enums.Status;
import com.sparta.currency_user.exception.*;
import com.sparta.currency_user.exception.code.ErrorCode;
import com.sparta.currency_user.repository.ExchangeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final ExchangeRepository exchangeRepository;
    private final CurrencyService currencyService;

    public CreateExchangeResDto createExchange(CreateExchangeReqDto dto, User loginUser) {
        //환전 요청 저장
        //저장할 화폐 정보 조회
        Currency currency = currencyService.findByCurrencyCode(dto.getCurrencyCode());

        UserCurrency userCurrency = UserCurrency.builder()
                .user(loginUser)
                .currency(currency)
                .fromAmount(dto.getMoney())
                .toAmount(exchangeMoney(dto.getMoney(),currency.getExchangeRate(),currency.getCurrencyCode()))
                .status(Status.NORMAL)
                .build();

        UserCurrency savedInfo = exchangeRepository.save(userCurrency);

        return new CreateExchangeResDto(savedInfo);
    }

    public List<ReadExchangeResDto> readExchanges(User loginUser) {
        List<UserCurrency> infoList = exchangeRepository.findAllByUser(loginUser);

        if(infoList.isEmpty()){
            throw new BaseException(ErrorCode.NOT_FOUND_EXCHANGES);
        }

        return infoList.stream().map(ReadExchangeResDto::new).toList();
    }

    @Transactional
    public Long cancelExchange(Long exchangeId, User loginUser) {
        UserCurrency userCurrency = exchangeRepository.findById(exchangeId).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_EXCHANGES));

        //수정하려는 정보가 자신의 환전 정보가 아니면
        if(!Objects.equals(userCurrency.getUser().getId(), loginUser.getId())){
            throw new BaseException(ErrorCode.INVALID_AUTHENTICATION);
        }

        userCurrency.updateStatus(Status.CANCELLED);

        return userCurrency.getId();
    }


    //
    private BigDecimal exchangeMoney(BigDecimal beforeExchange, BigDecimal exchangeRate, CurrencyCode code){

        switch (code){
            case USD -> {
                return beforeExchange.divide(exchangeRate,2,RoundingMode.HALF_UP);
            }
            case JPY -> {
                return beforeExchange.divide(exchangeRate,0,RoundingMode.HALF_UP);
            }
            case EUR -> {
                return beforeExchange.divide(exchangeRate,1,RoundingMode.HALF_UP);
            }
        }
        //아무것도 해당이 안되면
        throw new BaseException(ErrorCode.NOT_FOUND_CURRENCY);
    }

    public UserTotalInfo readTotalInfo(User user) {
        List<UserCurrency> allByUser = exchangeRepository.findAllByUser(user);

        if(allByUser.isEmpty()){
            throw new BaseException(ErrorCode.NOT_FOUND_EXCHANGES);
        }
        return exchangeRepository.findTotalAmountAndCount(user.getId());
    }
}
