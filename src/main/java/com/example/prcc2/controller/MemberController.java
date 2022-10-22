package com.example.prcc2.controller;

import com.example.prcc2.dto.CommonRes;
import com.example.prcc2.dto.SigninReqDto;
import com.example.prcc2.dto.SignupReqDto;
import com.example.prcc2.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberController {
//컨트롤러를 작성할때 서비스가 없으니 껍대기 값을 먼저 작성하기
    private MemberService memberService; //(타입/변수명)

    @PostMapping("/signup")//설정한 api에서 @PostMapping()안에 url을 제외하고 HTTP 메소드를 작성함
    //                                      앞에가 타입 뒤에가 변수명 dto 입니다 알아보기 쉽게 변수명을 설정했습니다.
    public CommonRes<?> signup(@RequestBody SignupReqDto dto){//클라이언트에서 가져오는 정보를 @RequestBody 를 사용해 HTTp BODY에서 들고옵니다.
        return new CommonRes<>(true,null); //API에서 CommonRes 구조가 success: ture data: null이였기 때문에 똑같이 줍니다.
    }

    @PostMapping("/login")
    public CommonRes<?> login(@RequestBody SigninReqDto dto){
        return new CommonRes<>(true,null);

    }
}
