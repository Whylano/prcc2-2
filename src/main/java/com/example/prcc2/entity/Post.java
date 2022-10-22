package com.example.prcc2.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends TimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String author;

    //jpa cascade, jpa fetchtupe  //관계를 맺고 난뒤에 생성자 확인!!!!!!!
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    public Post(Member member, String title, String content){ //생성자. //순서를 주의해야함

        this.member = member;
        this.title = title;     //this, new 차이 찾아보기
        this.content = content;
        this.author = member.getNickname();
    }

}
