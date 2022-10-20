package com.example.prcc2.dto;

import com.example.prcc2.entity.Member;
import lombok.Getter;

@Getter
public class SignupReqDto {

    private String nickname;
    private String password;

    private String passwordCheck;
//dot를 entity로 변경해주는 메소드//멤버로 바꿔준다라는 뜻
    public Member toEntity(){
        return new Member(this.nickname, this.password);
    }
}
