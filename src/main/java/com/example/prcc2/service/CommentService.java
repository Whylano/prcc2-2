package com.example.prcc2.service;

import com.example.prcc2.dto.CommentReqDto;
import com.example.prcc2.dto.CommentResDto;
import com.example.prcc2.dto.PostResDto;
import com.example.prcc2.entity.Comment;
import com.example.prcc2.entity.Member;
import com.example.prcc2.entity.Post;
import com.example.prcc2.repository.CommentRepository;
import com.example.prcc2.repository.MemberRepository;
import com.example.prcc2.repository.PostRepository;
import com.example.prcc2.utill.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

//1. 댓글 작성
//2. 댓글 목록 조회
//3. 댓글 수정
//4. 댓글 삭제
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Transactional
    public void create(Long postId,CommentReqDto dto){


        Long memberId = SecurityUtil.getCurrentMemberId();

        Member member = memberRepository.findById(memberId).orElseThrow(//repository에서 멤버 아이디를 준비합니다..orElseThrow로 해당아이디가 아니면 던져라
                () -> new IllegalArgumentException("해당 아이디를 가진 멤버의 아이디가 없습니다.")
        );
        Post post = postRepository.findById(postId).orElseThrow(  //Null이면 던저라
                () -> new IllegalArgumentException("해당 아이디를 가진 게시글이 존재하지 않습니다.")
        );

        Comment comment = dto.toEntity(member,post);

        commentRepository.save(comment);
    }
    public List<CommentResDto> readAll(Long postId){

        List<Comment> commentList = commentRepository.findAllById(postId);

        List<CommentResDto> commentResDtoList = commentList.stream()
                .map(comment -> new CommentResDto(comment))
                .collect(Collectors.toList()) ;

        return commentResDtoList;
    }
}

