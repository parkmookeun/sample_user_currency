package com.sparta.currency_user.controller;

import com.sparta.currency_user.constants.AuthConstants;
import com.sparta.currency_user.dto.CreateExchangeReqDto;
import com.sparta.currency_user.dto.CreateExchangeResDto;
import com.sparta.currency_user.dto.ReadExchangeResDto;
import com.sparta.currency_user.dto.UserTotalInfo;
import com.sparta.currency_user.entity.User;
import com.sparta.currency_user.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exchanges")
public class ExchangeController {

    private final ExchangeService exchangeService;
    //환전 요청
    // 유저 정보, 화폐 정보, 환전할 양, 환전된 양, 상태
    //
    @PostMapping
    public ResponseEntity<CreateExchangeResDto> createExchange(
            @RequestBody CreateExchangeReqDto dto,
            @SessionAttribute(AuthConstants.LOGIN_USER) User loginUser
    ){

        CreateExchangeResDto resDto = exchangeService.createExchange(dto, loginUser);

        return new ResponseEntity<>(resDto, HttpStatus.OK);
    }

    //환전 정보 조회
    @GetMapping
    public ResponseEntity<List<ReadExchangeResDto>> readExchanges(
            @SessionAttribute(AuthConstants.LOGIN_USER) User loginUser
    ){
        List<ReadExchangeResDto> resDto = exchangeService.readExchanges(loginUser);

        return new ResponseEntity<>(resDto, HttpStatus.OK);
    }

    //총 정보 조회
    @GetMapping("/total")
    public ResponseEntity<UserTotalInfo> readTotalExchangeInfo(
            @SessionAttribute(AuthConstants.LOGIN_USER) User loginUser
    ){
         UserTotalInfo totalInfo = exchangeService.readTotalInfo(loginUser);

        return new ResponseEntity<>(totalInfo, HttpStatus.OK);
    }

    //환전 정보 수정
    @PatchMapping("/{exchangeId}")
    public ResponseEntity<Long> updateExchange(
            @PathVariable Long exchangeId,
            @SessionAttribute(AuthConstants.LOGIN_USER) User loginUser
    ){
        Long id = exchangeService.cancelExchange(exchangeId, loginUser);

        return new ResponseEntity<>(id,HttpStatus.OK);
    }



    //환전 정보 삭제
    //-> 영속성 전이로 삭제

}
