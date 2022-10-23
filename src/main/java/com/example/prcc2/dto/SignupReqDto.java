package com.example.prcc2.dto;

import com.example.prcc2.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class SignupReqDto {

    private String nickname;
    private String password;

    private String passwordCheck;
//dto를 entity로 변경해주는 메소드//멤버로 바꿔준다라는 뜻)
    //회원가입을 하는 과정에서 Member를 생성해야하는데 이정보를 가지고 Member를 만드는데
    //이때 toEntity로 메소드를 만들어 놓으면

    // SignupReqDto signupReqDto= new SignupReqDto();
    // Member member = new Member(signupReqDto.getNikcnake(),...);을 따로 코드로 계속 만들어줘야하지만
    //toEntity()로 만들어주면 Member.java내용을 이용해 new Member안에 생성자 값을 넣어줘서 쉽게 활용한다.

    public Member toEntity(String encodedPassword){
        return new Member(this.nickname, encodedPassword);
    }
}
