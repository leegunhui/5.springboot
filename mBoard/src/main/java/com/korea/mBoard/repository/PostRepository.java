package com.korea.mBoard.repository;

import com.korea.mBoard.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // 기본 CRUD 메서드 모두 제공됨
}
