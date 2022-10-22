package com.example.prcc2.controller;

import com.example.prcc2.dto.CommonRes;
import com.example.prcc2.dto.PostDetailResDto;
import com.example.prcc2.dto.PostReqDto;
import com.example.prcc2.dto.PostResDto;
import com.example.prcc2.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/Posts")
public class PostController {
    private final PostService postService;

    @PostMapping
    public CommonRes<?> create(@RequestBody PostReqDto dto){

        postService.create(dto);

        return new CommonRes<>(true,null);
    }
    @GetMapping
    public CommonRes<?> getAll(){

        List<PostResDto> resDtos = postService.readAll();

        return new CommonRes<>(true,resDtos);
    }
    @GetMapping("/{id}")
    public CommonRes<?> getOneById(@PathVariable("id") Long id){

        PostDetailResDto resDto = postService.readOneById(id);

        return new CommonRes<>(true,resDto);
    }
    @PutMapping("/{id}")
        public CommonRes<?> update(@PathVariable("id") Long id, @RequestBody PostReqDto dto){

        postService.update(id,dto);

        return new CommonRes<>(true, null);
    }
    @DeleteMapping("/{id}")
    public CommonRes<?> delete(@PathVariable("id") Long id){

        postService.delete(id);

        return new CommonRes<>(true, null);
    }
}
