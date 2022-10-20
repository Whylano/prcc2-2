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

    private MemberService memberService; //(타입/변수명)

    @PostMapping("/signup")
    public CommonRes<?> signup(@RequestBody SignupReqDto dto){//클라이언트에서 가져오는 정보
        return new CommonRes<>(true,null);
    }
    @PostMapping("/login")
    public CommonRes<?> login(@RequestBody SigninReqDto dto){
        return new CommonRes<>(true,null);

    }
}
