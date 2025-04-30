package com.korea.todo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.korea.todo.model.ResponseDTO;
import com.korea.todo.service.TodoService;

@RestController
@RequestMapping("todo")
public class TodoController {
	
	//실행할 때 service객체가 필드로 직접 주입이된다
	@Autowired
	private TodoService service;
	
	//주입받은 객체로 메서드를 실행하면된다
	@GetMapping("/test")
	public ResponseEntity<?> testTodo(){
		String str = service.testService();//테스트 서비스 사용
		List<String> list = new ArrayList<>();
		list.add(str);
		ResponseDTO<String>response = ResponseDTO.<String>builder().data(list).build();
		return ResponseEntity.ok().body(response);
		
	}
}