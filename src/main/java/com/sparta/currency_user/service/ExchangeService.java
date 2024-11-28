package com.sparta.currency_user.service;

import com.sparta.currency_user.dto.CreateExchangeReqDto;
import com.sparta.currency_user.dto.CreateExchangeResDto;
import com.sparta.currency_user.dto.ReadExchangeResDto;
import com.sparta.currency_user.dto.UserTotalInfo;
import com.sparta.currency_user.entity.Currency;
import com.sparta.currency_user.entity.User;
import com.sparta.currency_user.entity.UserCurrency;
import com.sparta.currency_user.entity.enums.Status;
import com.sparta.currency_user.exception.NoDataAccessAuthorization;
import com.sparta.currency_user.exception.NoSuchExchangeInfoException;
import com.sparta.currency_user.exception.NotChangedException;
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
        String currencyName = dto.getCurrencyName();
        //저장할 화폐 정보 조회
        Currency currency = currencyService.findCurrencyByName(currencyName);

        UserCurrency userCurrency = UserCurrency.builder()
                .user(loginUser)
                .currency(currency)
                .fromAmount(dto.getMoney())
                .toAmount(exchangeMoney(dto.getMoney(),currency.getExchangeRate()))
                .status(Status.NORMAL)
                .build();

        UserCurrency savedInfo = exchangeRepository.save(userCurrency);

        return new CreateExchangeResDto(savedInfo);
    }

    public List<ReadExchangeResDto> readExchanges(User loginUser) {
        List<UserCurrency> infoList = exchangeRepository.findAllByUser(loginUser);

        if(infoList.isEmpty()){
            throw new NotChangedException("아직 환전을 신청한 적이 없습니다!");
        }

        return infoList.stream().map(ReadExchangeResDto::new).toList();
    }

    @Transactional
    public Long updateExchange(Long exchangeId, User loginUser) {
        UserCurrency userCurrency = exchangeRepository.findById(exchangeId).orElseThrow(
                () -> new NoSuchExchangeInfoException(exchangeId + "에 해당하는 환전 정보가 없습니다!"));

        //수정하려는 정보가 자신의 환전 정보가 아니면
        if(!Objects.equals(userCurrency.getUser().getId(), loginUser.getId())){
            throw new NoDataAccessAuthorization("접근하려는 정보에 대한 권한이 없습니다!");
        }

        userCurrency.updateStatus(Status.CANCELLED);

        return userCurrency.getId();
    }


    //
    private BigDecimal exchangeMoney(BigDecimal beforeExchange, BigDecimal exchangeRate){

        return beforeExchange.divide(exchangeRate,2,RoundingMode.HALF_UP);
    }

    public UserTotalInfo readTotalInfo(User user) {
        List<UserCurrency> allByUser = exchangeRepository.findAllByUser(user);

        if(allByUser.isEmpty()){
            throw new NotChangedException(user.getName() + "의 총합 정보가 없습니다.");
        }
        return exchangeRepository.findTotalAmountAndCount(user.getId());
    }
}
