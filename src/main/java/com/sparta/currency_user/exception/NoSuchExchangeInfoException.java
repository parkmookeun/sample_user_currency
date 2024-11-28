package com.sparta.currency_user.exception;

public class NoSuchExchangeInfoException extends RuntimeException {
    public NoSuchExchangeInfoException(String message) {
        super(message);
    }
}
