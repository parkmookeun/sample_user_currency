package com.sparta.currency_user.service;

import com.sparta.currency_user.dto.CurrencyRequestDto;
import com.sparta.currency_user.dto.CurrencyResponseDto;
import com.sparta.currency_user.entity.Currency;
import com.sparta.currency_user.entity.enums.CurrencyCode;
import com.sparta.currency_user.repository.CurrencyRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CurrencyService {
    private static final BigDecimal borderValue = new BigDecimal("1000");
    private final CurrencyRepository currencyRepository;

    public CurrencyResponseDto findById(Long id) {
        return new CurrencyResponseDto(findCurrencyById(id));
    }

    public Currency findCurrencyById(Long id) {
        return currencyRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("통화를 찾을 수 없습니다."));
    }

    public Currency findByCurrencyCode(CurrencyCode code) {
        log.info("코드로 통화 찾기: {}", code.toString());
        return currencyRepository.findByCurrencyCode(code).orElseThrow(() -> new IllegalArgumentException("통화를 찾을 수 없습니다."));
    }

    public List<CurrencyResponseDto> findAll() {
        return currencyRepository.findAll().stream().map(CurrencyResponseDto::toDto).toList();
    }

    @Transactional
    public CurrencyResponseDto save(CurrencyRequestDto currencyRequestDto) {
        Currency savedCurrency = currencyRepository.save(currencyRequestDto.toEntity());
        return new CurrencyResponseDto(savedCurrency);
    }

    @PostConstruct
    public void validateExchangeRates() {
        List<Currency> currencies = currencyRepository.findAll();

        for (Currency currency : currencies) {
            BigDecimal exchangeRate = currency.getExchangeRate();

            // 환율이 null, 0 이하이거나, 범위를 벗어난 경우
            if (exchangeRate == null || exchangeRate.compareTo(BigDecimal.ZERO) <= 0 || exchangeRate.compareTo(borderValue) > 0) {
                log.info("환율값: {} 이 유효하지 않습니다.",exchangeRate);
                // 예외를 던지거나 환율을 수정
                throw new IllegalArgumentException("잘못된 환율 데이터: " + currency.getExchangeRate());
            }
        }
        System.out.println("모든 환율이 유효합니다.");
    }
}
