package com.example.prcc2.controller;

import com.example.prcc2.dto.CommentReqDto;
import com.example.prcc2.dto.CommentResDto;
import com.example.prcc2.dto.CommonRes;
import com.example.prcc2.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts/{post_id}/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public CommonRes<?> create(@PathVariable("post_id") Long postId, @RequestBody CommentReqDto dto){
        commentService.create(postId,dto);
        return new CommonRes<>(true,null);
    }
    //특정 게시물 댓글 조회
    @GetMapping
    public CommonRes<?> getAllByPostId(@PathVariable("post_id") Long postId){
        List<CommentResDto> resDtos = commentService.readAll(postId);
        return new CommonRes<>(true,resDtos);
    }

    @PutMapping("/{comment_id}")
    public CommonRes<?> update(@PathVariable("post_id") Long postId,
                               @PathVariable("comment_id") Long commentId,
                               @RequestBody CommentReqDto dto) {

        commentService.update(postId, commentId, dto);
        return new CommonRes<>(true, null);
    }

    @DeleteMapping("/{comment_id}")
    public CommonRes<?> delete(@PathVariable("post_id") Long postId,
                               @PathVariable("comment_id") Long commentId){

        commentService.delete(postId, commentId);
        return new CommonRes<>(true, null);
    }

}
