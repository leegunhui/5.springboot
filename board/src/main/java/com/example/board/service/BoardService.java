package com.example.board.service;

import org.springframework.stereotype.Service;

import com.example.board.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	private final BoardRepository repository;
}
