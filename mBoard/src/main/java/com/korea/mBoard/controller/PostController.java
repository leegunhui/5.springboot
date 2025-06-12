package com.korea.mBoard.controller;

import com.korea.mBoard.dto.PostDTO;
import com.korea.mBoard.dto.ResponseDTO;
import com.korea.mBoard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 전체 게시글 리스트 조회
    @GetMapping
    public ResponseEntity<ResponseDTO<PostDTO>> getAllPosts() {
        List<PostDTO> posts = postService.findAll();
        ResponseDTO<PostDTO> response = new ResponseDTO<>(null, posts);
        return ResponseEntity.ok(response);
    }

    // 단일 게시글 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<PostDTO>> getPostById(@PathVariable("id") Long id) {
        try {
            PostDTO post = postService.findById(id);
            ResponseDTO<PostDTO> response = new ResponseDTO<>(null, List.of(post));
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ResponseDTO<PostDTO> response = new ResponseDTO<>(e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }

    // 새 게시글 등록
    @PostMapping
    public ResponseEntity<ResponseDTO<PostDTO>> createPost(@RequestBody PostDTO postDTO) {
        PostDTO savedPost = postService.save(postDTO);
        ResponseDTO<PostDTO> response = new ResponseDTO<>(null, List.of(savedPost));
        return ResponseEntity.ok(response);
    }
}
