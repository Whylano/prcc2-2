package com.example.prcc2.controller;

import com.example.prcc2.dto.CommentReqDto;
import com.example.prcc2.dto.CommonRes;
import com.example.prcc2.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts/{post_id}/comments")
public class CommentController {
    private CommentService commentService;

    @PostMapping
    public CommonRes<?> create(@PathVariable("post_id") Long postId, @RequestBody CommentReqDto dto){
        commentService.create(postId,dto);
        return new CommonRes<>(true,null);
    }
    //특정 게시물 댓글 조회
    @GetMapping
    public CommonRes<?> getAllByPostId(@PathVariable("post_id") Long postId){
        return new CommonRes<>(true,null);
    }

    @PutMapping("/{id}")
    public CommonRes<?> update(@PathVariable("post_id") Long postId,
                               @PathVariable("id")Long commentId,
                               @RequestBody CommentReqDto dto){
        return new CommonRes<>(true,null);
    }
    @DeleteMapping("/{comment_id}")
    public CommonRes<?> delete(@PathVariable("post_id")Long postId,
                              @PathVariable("comment_id")Long commentId){
        return new CommonRes<>(true,null);
    }
}
