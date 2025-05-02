package com.korea.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.korea.todo.model.TodoEntity;
import com.korea.todo.persistence.TodoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
//스프링 프레임워크에서 제공하는 어노테이션중 하나로, 서비스 레이어에 사용되는
//클래스를 명시랗 때 사용
//이 어노테이션을 사용하면 스프링이 해당 클래스를 스프링 컨테이너에서 관리하는
//빈(bean)으로 등록하고, 비즈니스 로직을 처리하는 역할을 맡는다.
public class TodoService {

	@Autowired
	private TodoRepository repository;
	
	
	public String testService() {
		// 엔티티 생성
		TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
		// TodoEntity 저장
		repository.save(entity);
		// TodoEntity 검색
		// findById(entity.getId()) : SELECT * FROM Todo WHERE id = ?;
		TodoEntity savedEntity = repository.findById(entity.getId()).get();
		return savedEntity.getTitle();
	}
	
	public List<TodoEntity> create(TodoEntity entity) {
		// 매개변수로 넘어온 Entity가 유효한지 검사한다.
		validate(entity);

		// Spring Data JPA의 리포지토리 메서드로, 전달된 entity를 데이터베이스에 저장한다.
		// 이 메서드는 JPA에서 제공하는 CRUD 기능 중 하나로, 기본적으로 엔티티가 데이터베이스에 존재하지 않으면 INSERT, 존재하면
		// UPDATE 쿼리를 실행한다.
		repository.save(entity);

		// 여기서 SLF4J의 플레이스홀더 {}가 사용되어, 로그 메시지에 엔티티의 ID가 삽입한다.
		log.info("Entity Id : {} is saved", entity.getId());

		// 특정 사용자 ID에 속한 모든 TodoEntity 목록을 반환한다.
		return repository.findByUserId(entity.getUserId());
	}

	public List<TodoEntity> retrieve(String userId) {
		return repository.findByUserId(userId);
	}

	public List<TodoEntity> update(TodoEntity entity) {
		//저장할 엔티티가 유효한지 확인한다.
		validate(entity);

		//넘겨받은 엔티티 id를 이용해 TodoEntity를 가져온다.
		//존재하지 않는 엔티티는 업데이트 할 수 없기 때문이다.
		Optional<TodoEntity> original = repository.findById(entity.getId());

		original.ifPresent(todo -> {
			//반환된 TodoEntity가 존재하면 값을 새 Entity값으로 덮어씌운다.
			todo.setTitle(entity.getTitle());
			todo.setDone(entity.isDone());

			//데이터베이스에 새 값을 저장한다.
			repository.save(todo);
		});

		return retrieve(entity.getUserId());
	}
	
	public List<TodoEntity> delete(TodoEntity entity){
		//저장할 엔티티가 유효한지 확인한다.
		validate(entity);
		
		try {
			//엔티티를 삭제한다.
			repository.delete(entity);
		} catch (Exception e) {
			// 예외 발생 시 id와 exception을 로깅한다.
			log.error("error deleting entity ",entity.getId(),e);
			
			//컨트롤러로 exception을 날린다. 데이터베이스 내부 로직을 캡슐화 하기 위해 e를 반환하지 않고 새로 exception 객체를 반환한다.
			throw new RuntimeException("error deleting entity "+entity.getId());
		}
		
		return retrieve(entity.getUserId());
	}
	
	private void validate(TodoEntity entity) {
		// 전달된 TodoEntity가 null인지 확인합니다. 만약 null이면 RuntimeException을 발생시키고, 경고 로그를 기록한다.
		if (entity == null) {
			log.warn("Entity cannot be null.");
			throw new RuntimeException("Entity cannot be null");
		}

		// TodoEntity 객체가 userId를 가지고 있는지 확인한다.
		if (entity.getUserId() == null) {
			log.warn("Unknown user");
			throw new RuntimeException("Unknown user");
		}
	}

}