package com.dfocus2.springboot.service.posts;

import com.dfocus2.springboot.domain.posts.Posts;
import com.dfocus2.springboot.domain.posts.PostsRepository;
import com.dfocus2.springboot.model.Header;
import com.dfocus2.springboot.model.Pagination;
import com.dfocus2.springboot.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(()->
        new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(()->
        new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        postsRepository.delete(posts);
    }

    public PostsResponseDto findById (Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    //Vue.js 게시글 전체 조회용 메소드
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    //Paging 기능을 구현하기 위한 페이징 조회용 메소드
    public Header<List<PostsListResponseDto>> getPostsList(Pageable pageable, SearchRequestDto search) {
        List<PostsListResponseDto> dtos = new ArrayList<>();
        Page<Posts> postsEntities;

        //검색 키워드에 맞춰 switch 처리
        switch(search.getKey()) {
            case "author": // 작성자
                postsEntities = postsRepository.findAllByAuthorContainingOrderByIdDesc(pageable, search.getValue());
                break;
            case "title": // 제목
                postsEntities = postsRepository.findAllByTitleContainingOrderByIdDesc(pageable, search.getValue());
                break;
            case "content": // 내용
                postsEntities = postsRepository.findAllByContentContainingOrderByIdDesc(pageable, search.getValue());
                break;
            default: //- 선택 - 의 경우 전체조회
                postsEntities = postsRepository.findAllByOrderByIdDesc(pageable);
                break;
        }
        for(Posts entity : postsEntities) {
            PostsListResponseDto dto = new PostsListResponseDto(entity);
            dtos.add(dto);
        }

        Pagination pagination = new Pagination(
                (int) postsEntities.getTotalElements()
                ,pageable.getPageNumber() + 1
                ,pageable.getPageSize()
                , 10);
        return Header.OK(dtos, pagination);
    }
}
