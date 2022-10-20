package com.example.prcc2.service;

import com.example.prcc2.dto.PostReqDto;
import com.example.prcc2.entity.Member;
import com.example.prcc2.entity.Post;
import com.example.prcc2.repository.MemberRepository;
import com.example.prcc2.repository.PostRepository;
import com.example.prcc2.utill.SecurityUtill;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Transactional //안에서 데이터 변경이 필요할 것같다 할때 안에서 써줘야함
    //게시글을 생성하기 위해서는 뭐가 필요할까요?- 생성자를 봅시다!!!- title, content, member
    public void create(PostReqDto dto){

        //member가 없으니 현재 로그인한 멤버의 아이를 가져온다.//SecurityUill에거 가져옴
        Long memberId = SecurityUtill.getCurrentMemberId();

        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디를 가진 멤버의 아이디가 없습니다.")
        );

        Post post = dto.toEntity(member);

        postRepository.save(post);
    }

}
