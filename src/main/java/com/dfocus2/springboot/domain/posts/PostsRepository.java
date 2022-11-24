package com.dfocus2.springboot.domain.posts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts,Long>{
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();

    Page<Posts> findAllByOrderByIdDesc(Pageable pageable);

    // 작성자 기준 조회
    Page<Posts> findAllByAuthorContainingOrderByIdDesc(Pageable pageable, String author);

    // 제목 기준 조회
    Page<Posts> findAllByTitleContainingOrderByIdDesc(Pageable pageable, String title);

    // 내용 기준 조회
    Page<Posts> findAllByContentContainingOrderByIdDesc(Pageable pageable, String content);
}
