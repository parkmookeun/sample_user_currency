package com.sparta.currency_user.dto;

import lombok.Getter;

@Getter
public class LoginRequestDto {
    private String email;
    private String password;

    LoginRequestDto(String email, String password){
        this.email = email;
        this.password =password;
    }
}
