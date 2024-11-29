package com.sparta.currency_user.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /**
     * Server
     */
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "유효성 검사 실패"),
    CONSTRAINT_VIOLATION(HttpStatus.CONFLICT, "제약 조건 위반"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류가 발생하였습니다."),

    /**
     * Common
     */
    NOT_FOUND_ENUM_CONSTANT(HttpStatus.NOT_FOUND, "열거형 상수값을 찾을 수 없습니다."),
    IS_NULL(HttpStatus.BAD_REQUEST, "NULL 값이 들어왔습니다."),
    COMMON_INVALID_PARAM(HttpStatus.BAD_REQUEST, "요청한 값이 올바르지 않습니다."),
    NO_SUCH_METHOD(HttpStatus.BAD_REQUEST, "메소드를 찾을 수 없습니다."),

    /**
     * Authentication
     */
    UNAUTHORIZED_ACCESS(HttpStatus.FORBIDDEN, "로그인을 해주세요."),
    INVALID_AUTHENTICATION(HttpStatus.BAD_REQUEST, "인증이 올바르지 않습니다."),


    /**
     * User
     */
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "회원 정보를 찾을 수 없습니다."),
    EXIST_EMAIL(HttpStatus.BAD_REQUEST, "중복된 이메일입니다."),
    EXIST_USERNAME(HttpStatus.BAD_REQUEST, "중복된 이름입니다."),
    ALREADY_DELETED_USER(HttpStatus.BAD_REQUEST, "이미 삭제된 유저 입니다."),

    /**
     * Currency
     */
    NOT_FOUND_CURRENCY(HttpStatus.NOT_FOUND, "통화 코드를 찾을 수 없습니다."),

    /**
     * UserCurrency
     */
    NOT_FOUND_EXCHANGES(HttpStatus.NOT_FOUND,"환전 내역을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
