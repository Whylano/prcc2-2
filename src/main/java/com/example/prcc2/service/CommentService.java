package com.example.prcc2.service;

import com.example.prcc2.dto.CommentReqDto;
import com.example.prcc2.dto.CommentResDto;
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
    //댓글 수정
//
    @Transactional
    public void update(Long postId, Long commentId, CommentReqDto dto){

//1.데이터 베이스에 있는 기존 데이터를 가져온다
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new IllegalArgumentException("해당아이디를 가진 댓글이 존재하지 않습니다.")
        );

        checkOwner(comment, SecurityUtil.getCurrentMemberId());

        checkPostByPostId(comment, postId);

//2.기존 데이터를 새로운 데이터로 수정합니다
        comment.update(dto.getContent());

//3.변경된 데이터를 데이터베이스에 저장합니다
        commentRepository.save(comment); //???? 왜 지웠지?
    }

    //댓글 삭제
    @Transactional
    public void delete(Long postId, Long commentId){

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디를 가진 댓글이 존재하지 않습니다.")
        );

        checkOwner(comment, SecurityUtil.getCurrentMemberId());

        checkPostByPostId(comment, postId);

        commentRepository.deleteById(commentId);
    }

    private void checkOwner(Comment comment, Long memberId){
        if(!comment.checkOwnerByMemberId(memberId)){
            throw new IllegalArgumentException("회원님이 작성한 댓글이 아닙니다.");
        }
    }

    private void checkPostByPostId(Comment comment, Long postId){

        if(!comment.checkPostByPostId(postId)){
            throw new IllegalArgumentException("해당 게시글의 댓글이 아닙니다.");
        }
    }
}

