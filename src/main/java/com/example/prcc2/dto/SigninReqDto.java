package com.example.prcc2.dto;
//로그인 DTO
import lombok.Data;

@Data
public class SigninReqDto {
    //아이디
    private String nickname;
    //비밀번호
    private String password;
}
