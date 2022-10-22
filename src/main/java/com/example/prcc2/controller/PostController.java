package com.example.prcc2.controller;

import com.example.prcc2.dto.CommonRes;
import com.example.prcc2.dto.PostReqDto;
import com.example.prcc2.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/Posts")
public class PostController {
    private final PostService postService;

    @PostMapping
    public CommonRes<?> create(@RequestBody PostReqDto dto){
        return new CommonRes<>(true,null);
    }
    @GetMapping
    public CommonRes<?> getAll(){
        return new CommonRes<>(true,null);
    }
    @GetMapping("/{id}")
    public CommonRes<?> getOneById(@PathVariable("id") Long id){
        return new CommonRes<>(true,null);
    }
    @PutMapping("/{id}")
        public CommonRes<?> update(@PathVariable("id") Long id, @RequestBody PostReqDto dto){
        return new CommonRes<>(true, null);
    }
    @DeleteMapping("/{id}")
    public CommonRes<?> delete(@PathVariable("id") Long id){
        return new CommonRes<>(true, null);
    }
}
