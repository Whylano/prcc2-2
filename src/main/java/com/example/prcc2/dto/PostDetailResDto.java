package com.example.prcc2.dto;

import com.example.prcc2.entity.Post;

import java.time.LocalDateTime;
//db에서 post타입으로 들고오고 //클라이언트에게 postDetailResDto타입으로 변환해준다
public class PostDetailResDto {

    private Long id;

    private String title;

    private String author;

    private String content;

    private LocalDateTime createdAt;

    public PostDetailResDto(Post post){

     this.id= post.getId();
     this.title = post.getTitle();
     this.author = post.getAuthor();
     this.content = post.getContent();
     this.createdAt = post.getCreatedAt().toLocalDateTime(); //.toLocalDate time
 }
}
