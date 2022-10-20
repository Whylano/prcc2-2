package com.example.prcc2.dto;

import com.example.prcc2.entity.Post;

import java.time.LocalDateTime;

public class PostResDto {
    private Long id;

    private String title;

    private String author;

    private LocalDateTime createdAt;

    public PostResDto(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.author = post.getAuthor();
        this.createdAt = post.getCreatedAt().toLocalDateTime();
    }

}
