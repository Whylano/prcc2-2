package com.example.prcc2.dto;

import com.example.prcc2.entity.Comment;
import com.example.prcc2.entity.Member;
import com.example.prcc2.entity.Post;
import lombok.Data;

@Data
public class CommentReqDto {
    private String content;

    public Comment toEntity(Member member, Post post){
        return new Comment(member, post, this.content);
    }
}
