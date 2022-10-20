package com.example.prcc2.dto;

public class CommonRes <T>{
    private Boolean success;

    private T data;

    public CommonRes(Boolean success, T data){
        this.success = success;
        this.data = data;
    }
}
