package com.example.board.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.board.dto.BoardDTO;
import com.example.board.model.BoardEntity;
import com.example.board.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository repository;

    // 게시물 전체 조회
    public List<BoardDTO> getAllPosts() {
        List<BoardEntity> boardEntities = repository.findAll();
        return boardEntities.stream()
                .map(BoardDTO::new) // BoardEntity를 BoardDTO로 변환
                .collect(Collectors.toList());
    }
    
    public BoardDTO createPost(BoardDTO boardDTO) {
        // DTO를 엔티티로 변환
        BoardEntity entity = BoardDTO.toEntity(boardDTO);
        // 게시물 저장
        BoardEntity savedEntity = repository.save(entity);
        // 저장된 엔티티를 DTO로 변환하여 반환
        return new BoardDTO(savedEntity);
    }
    
    // 게시물 수정
    public BoardDTO updatePost(Long id, BoardDTO boardDTO) {
        // 기존 게시물 조회
        BoardEntity existingPost = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시물이 존재하지 않습니다."));

        // 수정할 필드 값 반영
        existingPost.setTitle(boardDTO.getTitle());
        existingPost.setAuthor(boardDTO.getAuthor());
        existingPost.setContent(boardDTO.getContent());
        existingPost.setWritingTime(boardDTO.getWritingTime());

        // 저장된 게시물 반환
        BoardEntity updatedEntity = repository.save(existingPost);
        return new BoardDTO(updatedEntity);
    }
    
    // 게시물 삭제
    public boolean deletePost(Long id) {
        Optional<BoardEntity> optionalEntity = repository.findById(id);
        if (optionalEntity.isPresent()) {
        	repository.deleteById(id); // 게시물 삭제
            return true;  // 삭제 성공
        }
        return false;  // 게시물이 없으면 삭제 실패
    }
}
