package com.sparta.currency_user.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String,String>> baseHandler(BaseException e) {
        log.error("error : {}", e.getErrorCode().getMessage());

        HashMap<String, String> map = new HashMap<>();

        map.put("error", e.getErrorCode().toString());
        map.put("error_message", e.getErrorCode().getMessage());

        return new ResponseEntity<>(map,e.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String,String>> illegalStateHandler(HttpMessageNotReadableException e) {
        log.error("error : {}", e.getMessage());

        HashMap<String, String> map = new HashMap<>();

        map.put("error", e.getClass().getName());
        map.put("error_message", "잘못된 데이터 요청입니다!");

        return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
    }
}
