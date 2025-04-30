package com.korea.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.korea.todo.model.TodoEntity;
import com.korea.todo.persistence.TodoRepository;

@Service
//스프링 프레임워크에서 제공하는 어노테이션중 하나로, 서비스 레이어에 사용되는
//클래스를 명시랗 때 사용
//이 어노테이션을 사용하면 스프링이 해당 클래스를 스프링 컨테이너에서 관리하는
//빈(bean)으로 등록하고, 비즈니스 로직을 처리하는 역할을 맡는다.
public class TodoService {
	
	@Autowired
	private TodoRepository repository;

	public String testService() {
		//엔티티 생성
		TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
		//TodoEntity 저장
		repository.save(entity);
		//TodoEntity 검색
        //findById(entity.getId()) : SELECT * FROM Todo WHERE id = ?;
		TodoEntity savedEntity = repository.findById(entity.getId()).get();
		return  savedEntity.getTitle();
	}
}