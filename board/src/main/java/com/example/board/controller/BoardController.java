package com.example.board.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.ResponseDTO;
import com.example.board.service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService service;

    @GetMapping("/all")
    public ResponseDTO<BoardDTO> getAllPosts() {
        // 게시물 조회
        List<BoardDTO> boardList = service.getAllPosts();

        // 응답 DTO 생성
        ResponseDTO<BoardDTO> response = new ResponseDTO<>();
        response.setData(boardList);
        response.setError(null); // 에러가 없으면 null로 설정

        return response;
    }
    
    @PostMapping
    public ResponseEntity<ResponseDTO<BoardDTO>> createPost(@RequestBody BoardDTO boardDTO) {
        try {
            BoardDTO createdPost = service.createPost(boardDTO);
            ResponseDTO<BoardDTO> response = new ResponseDTO<>();
            response.setData(List.of(createdPost)); // 단일 게시물을 반환하므로 List에 담아서 보내기
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ResponseDTO<BoardDTO> response = new ResponseDTO<>();
            response.setError("게시물 추가 실패");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    // 게시물 수정 API 추가
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<BoardDTO>> updatePost(@PathVariable("id") Long id, @RequestBody BoardDTO boardDTO) {
        try {
            BoardDTO updatedPost = service.updatePost(id, boardDTO);
            ResponseDTO<BoardDTO> response = new ResponseDTO<>();
            response.setData(List.of(updatedPost));  // 수정된 게시물 하나를 List로 담아서 반환
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ResponseDTO<BoardDTO> response = new ResponseDTO<>();
            response.setError("게시물 수정 실패");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
 // 게시물 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") Long id) {
        boolean isDeleted = service.deletePost(id);  // 서비스 계층에서 삭제 요청을 처리

        if (isDeleted) {
            return ResponseEntity.noContent().build();  // 삭제 성공 시: 204 No Content 응답
        }
        return ResponseEntity.notFound().build();  // 게시물 존재하지 않으면: 404 Not Found 응답
    }
}
