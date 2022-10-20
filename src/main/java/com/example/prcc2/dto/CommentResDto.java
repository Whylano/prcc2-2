package com.example.prcc2.dto;

import com.example.prcc2.entity.Comment;

import java.time.LocalDateTime;

public class CommentResDto {

    private  Long id;

    private String content;

    private String author;

    private LocalDateTime createdAt;

    public CommentResDto(Comment comment){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.author = comment.getMember().getNickname(); //이건 문제가 발생할 수 있음!!
        this.createdAt = comment.getCreatedAt().toLocalDateTime();
    }
}
