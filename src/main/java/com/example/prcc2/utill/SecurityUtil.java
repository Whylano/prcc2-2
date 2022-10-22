package com.example.prcc2.utill;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
//사용법 : service에서 멤버의 정보가 없을때 사용하는 Security Utill이다.
public abstract class SecurityUtil {  //현제 로그인한 사람의 아이디를 가지고 올수있다.

    public static Long getCurrentMemberId(){//현재 아이디를 가져온 것입니다.

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || authentication.getName() == null){
            throw new RuntimeException("인증 정보가 존재하지 않습니다.");
        }

        return Long.parseLong(authentication.getName());
    }

}
