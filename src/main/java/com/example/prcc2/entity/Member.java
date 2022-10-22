package com.example.prcc2.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity //
public class Member extends TimeEntity{

    @GeneratedValue(strategy = GenerationType.IDENTITY) //아이디를 자동으로 생성할지 수기로 생성할지 작성함
    @Id
    private Long id;
    @Column(nullable = false,unique = true)
    private String nickname;
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MemberRole role;

    //생성자
    public Member(String nickname, String password){

        this.nickname = nickname;
        this.password = password;
        this.role = MemberRole.MEMBER;
    }

}
