package com.example.prcc2.service;

import com.example.prcc2.dto.SigninReqDto;
import com.example.prcc2.dto.SignupReqDto;
import com.example.prcc2.dto.TokenDto;
import com.example.prcc2.entity.Member;
import com.example.prcc2.repository.MemberRepository;
import com.example.prcc2.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder; //Authentication을 구성할 수 있는 애다(만들어 줄수 있는애다)
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    //1.닉네임이 중복되었는지
    //2.비밀번호와 비밀번호 확인이 동일한지
    //3.회원가입
    @Transactional
    public void signup(SignupReqDto dto){ //request body에 있는 정보인 dto 정보를 가져옵니다.
        validateNicknameDuplicated(dto.getNickname());                          //작성한 메소드를 사용하며 dto에 있는 nickname()을 가져와get줍니다.여기서 문제가 생기면 밑으로 내려오지않는다. 이유는 메소드에 예외처리를 했기 때문입니다.
        validatePasswordEquals(dto.getPassword(), dto.getPasswordCheck());      //여기도 마찬가지로 작성한 메소드를 사용합니다.
        String encodePw = passwordEncoder.encode(dto.getPassword());
        Member member = dto.toEntity(encodePw);
        memberRepository.save(member);                                          //회원가입 기능
    }
    //로그인 기능 구현
    //1.로그인 구현 - 아이디, 비밀번호를 받아서 검증 후 토큰 생성 후 반환
    public TokenDto login(SigninReqDto dto) {

        //인증이 되어있지 않은 Authentication 객체 생성
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(dto.getNickname(), dto.getPassword());

        //인증에 필요한 authentication manager를 authenticationManagerBuilder를 통해 얻어온다.
        AuthenticationManager authenticationManager = authenticationManagerBuilder.getObject();  //참고자료: https://iseunghan.tistory.com/m/368
        //---------(authenticated 구문과 authentication을 잘 구분해서 써야한다!!!주의필요)-----------
        //Authentication Manager에게 넘겨주어 인증을 진행하고, 인증에 성공하면 인증에 성공한 인증 객체를 반환한다.
        Authentication authenticated = authenticationManager.authenticate(authentication);

        TokenDto tokenDto = tokenProvider.generateToken(authenticated);
        return tokenDto;
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
