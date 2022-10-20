package com.example.prcc2.dto;

import com.example.prcc2.entity.Member;
import com.example.prcc2.entity.Post;
import lombok.Data;

@Data
public class PostReqDto {

    private String title;

    private String content;

    public Post toEntity(Member member){
        return new Post(member, this.title, this.content);
    }
}
