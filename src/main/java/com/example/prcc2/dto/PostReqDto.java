package com.example.prcc2.dto;

import com.example.prcc2.entity.Member;
import com.example.prcc2.entity.Post;
import lombok.Data;
//게시물 생성 기능 구현
//Post라는 entity를 써줘야 DB에 데이터를 보낼수 있다
//만약 장성중에 오류가 생기면 Post entity에서 확인해봐야함

//**게시물 수정 기능은 생성과 값이 똑같기 때문에 따로 dto를 만들 필요없이 이 메소드를 사용하면 됩니다.
@Data
public class PostReqDto {

    private String title;

    private String content;

    public Post toEntity(Member member){
        return new Post(member, this.title, this.content);
    }
}
