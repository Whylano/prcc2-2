package com.example.prcc2.dto;

import com.example.prcc2.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
//세부조회기능
//db에서 post타입으로 들고오고 //클라이언트에게 postDetailResDto타입으로 변환해준다
//db에서 Post 타입으로 가져온다.
public class PostDetailResDto {

    private Long id;

    private String title;

    private String author;

    private String content;

    private LocalDateTime createdAt;

    //db에서 Post 타입으로 가져왔기 때문에  생성자 인자값으로 Post post로 가져온다.
    public PostDetailResDto(Post post) {

        this.id = post.getId();
        this.title = post.getTitle();
        this.author = post.getAuthor();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt().toLocalDateTime(); //.toLocalDate time
    }

    //데이터 수정

}
