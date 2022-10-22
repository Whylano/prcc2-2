package com.example.prcc2.dto;
//로그인 DTO
import lombok.Data;

@Data
public class SigninReqDto {

    private String nickname;

    private String password;
}
