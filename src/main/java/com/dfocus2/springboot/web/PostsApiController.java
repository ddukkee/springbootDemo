package com.dfocus2.springboot.web;

import com.dfocus2.springboot.model.Header;
import com.dfocus2.springboot.service.posts.PostsService;
import com.dfocus2.springboot.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById (@PathVariable Long id){
        return postsService.findById(id);
    }

    //페이징 기법을 이용한 조회 방식 변경으로 기존 코드 주석처리
    //@GetMapping("/api/v1/posts/list")
    //public List<PostsListResponseDto> postsList() {
    //    return postsService.findAllDesc();
    //}

    @GetMapping("/api/v1/posts/list")
    public Header<List<PostsListResponseDto>> postsList(Pageable pageable, SearchRequestDto search) {
        return postsService.getPostsList(pageable, search);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }
}
