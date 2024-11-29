package com.sparta.currency_user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequestDto {
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    LoginRequestDto(String email, String password){
        this.email = email;
        this.password =password;
    }
}
