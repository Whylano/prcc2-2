package com.example.prcc2.dto;
//api 구조
// success: true
// date :[]

import lombok.Getter;

@Getter
public class CommonRes <T>{
    private Boolean success;

    //제네릭<T>를 쓰는 이유는
    // 만약 data 타입을 string으로 넣고 싶다면
    // CommonRes<String>넣고 싶은 것을 넣을 수 있다.
    private T data;

    public CommonRes(Boolean success, T data){
        this.success = success;
        this.data = data;
    }
}
