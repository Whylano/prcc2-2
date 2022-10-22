package com.example.prcc2.dto;

import com.example.prcc2.entity.Post;

import java.time.LocalDateTime;
//***처음에 데이터를 가져오면 DB에서 가져오게되는데,  data타입이 게시글 형태(Post객체 타입)로 가져오게된다.
//@Entity를 사용하는 이유는 db와 Entity가 주고받기 위해서 쓰는 어노테이션이다.
//Post타입을 PostResDto타입으로 들고와서 변경을 해줘야한다.
public class PostResDto {
    private Long id;

    private String title;

    private String author;

    private String content;

    private LocalDateTime createdAt;
//Post타입에서 PostResDto타입으로 가져올꺼기 때문에  Post post를 인자로 받아준다.
    public PostResDto(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.author = post.getAuthor();
        this.createdAt = post.getCreatedAt().toLocalDateTime();
    }

}
