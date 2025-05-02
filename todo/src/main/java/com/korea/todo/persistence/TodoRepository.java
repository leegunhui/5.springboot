package com.korea.todo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.korea.todo.model.TodoEntity;

@Repository
//주로 데이터베이스와 상호작용하는 클래스에서 사용되며, CRUD와 같은 데이터베이스 작업을 처리하는데 사용된다
//@Component의 자식 어노테이션이므로 자동으로 bean으로 등록된다
//다른 계층에서 주입받아 사용할 수 있다.
public interface TodoRepository extends JpaRepository<TodoEntity, String>{
	//t.userId=?1
	//userId필드가 주어진 파라미터와 일치하는 TodoEntity 객체들을 조회하는 조건
	 @Query("SELECT t FROM TodoEntity t where t.userId=?1")
		List<TodoEntity> findByUserIdQuery(String userId);
	 
//	 JPQL의 특징
//	 객체 지향적
//	 JPQL은 데이터베이스 테이블 대신 JPA 엔티티 객체를 대상으로 쿼리를 실행합니다.
//	 테이블 이름이나 컬럼 대신 엔티티 클래스 이름과 필드를 사용하여 쿼리를 작성한다.
//	 이 때문에, JPQL 쿼리는 데이터베이스 독립적이며, 데이터베이스의 스키마에 의존하지 않는다.
//	 SQL과 유사한 구문
//	 JPQL은 SQL과 매우 유사한 문법을 사용하지만, 엔티티 객체를 다룬다는 점에서 차이가 있다.
//	 예를 들어, SQL에서 SELECT * FROM users는 JPQL에서는 SELECT u FROM User u로 표현된다.
//	 여기서 User는 데이터베이스 테이블이 아닌 엔티티 클래스를 의미한다.
	 
	 List<TodoEntity> findByUserId(String userId);
}