package com.korea.mBoard.service;

import com.korea.mBoard.domain.Post;
import com.korea.mBoard.dto.PostDTO;
import com.korea.mBoard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    // 전체 게시글 조회
    public List<PostDTO> findAll() {
        return postRepository.findAll()
                .stream()
                .map(PostDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // 단일 게시글 조회
    public PostDTO findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. id=" + id));
        return PostDTO.fromEntity(post);
    }

    // 새 게시글 저장
    public PostDTO save(PostDTO postDTO) {
        Post post = postDTO.toEntity();
        Post saved = postRepository.save(post);
        return PostDTO.fromEntity(saved);
    }
}
