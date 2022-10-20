package com.example.prcc2.service;

import com.example.prcc2.dto.SignupReqDto;
import com.example.prcc2.entity.Member;
import com.example.prcc2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private MemberRepository memberRepository;
    //1.닉네임이 중복되었는지
    //2.비밀번호와 비밀번호 확인이 동일한지
    //3.회원가입
    @Transactional
    public void signup(SignupReqDto dto){
        validateNicknameDuplicated(dto.getNickname());
        validatePasswordEquals(dto.getPassword(), dto.getPasswordCheck());

        Member member = dto.toEntity();
        memberRepository.save(member);
    }

    private void validateNicknameDuplicated(String nickname){
        boolean existsByNickname = memberRepository.existsByNickname(nickname);
        if(existsByNickname){
            throw new IllegalArgumentException("중복된 닉네임 입니다.");
        }
    }
    private void validatePasswordEquals(String password, String passwordCheck){
        if(!password.equals(passwordCheck)){
            throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }
    }
}
