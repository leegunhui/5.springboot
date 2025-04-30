package com.korea.todo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.korea.todo.model.TodoEntity;

@Repository
//주로 데이터베이스와 상호작용하는 클래스에서 사용되며, CRUD와 같은 데이터베이스 작업을 처리하는데 사용된다
//@Component의 자식 어노테이션이므로 자동으로 bean으로 등록된다
//다른 계층에서 주입받아 사용할 수 있다.
public interface TodoRepository extends JpaRepository<TodoEntity, String>{

}