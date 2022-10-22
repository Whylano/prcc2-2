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
    public void signup(SignupReqDto dto){ //request body에 있는 정보인 dto 정보를 가져옵니다.
        validateNicknameDuplicated(dto.getNickname());                          //작성한 메소드를 사용하며 dto에 있는 nickname()을 가져와get줍니다.여기서 문제가 생기면 밑으로 내려오지않는다. 이유는 메소드에 예외처리를 했기 때문입니다.
        validatePasswordEquals(dto.getPassword(), dto.getPasswordCheck());      //여기도 마찬가지로 작성한 메소드를 사용합니다.

        Member member = dto.toEntity();
        memberRepository.save(member);                                          //회원가입 기능
    }
//if문으로 쓰는 것 보다 메소드로 만들어준다음 상속받을 수 있게 합니다.
    private void validateNicknameDuplicated(String nickname){  //필요한 값만 (String nickname)을 받아옵니다.
        boolean existsByNickname = memberRepository.existsByNickname(nickname);
        if(existsByNickname){
            throw new IllegalArgumentException("중복된 닉네임 입니다."); //예외처리를 해줍니다.
        }
    }
    private void validatePasswordEquals(String password, String passwordCheck){
        if(!password.equals(passwordCheck)){                                        //password와 passwordcheck가 같은지 확인하는데 ==을 쓰면 안된다.(주소를 저장하기 때문이다)equals는 반대로(객체끼리 비교할때 쓰며 내용을 비교한다.)
            throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다."); //만약 비밀번호가 같다면 예외처리 해줍니다.
        }
    }
}
