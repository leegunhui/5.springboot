package com.korea.todo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
//자바 클래스를 엔티티로 지정하기 위해 사용한다.
//이름을 부여하고 싶다면 @Entity("TodoEntity") 처럼 매개변수를 넣을 수 있다.
@Entity
@Table(name="Todo")
public class TodoEntity {
	
	@Id //엔티티의 기본키필드를 나타낸다
	//@GeneratedValue(generator="system-uuid")
	//id값을 자동으로 생성해 넣어준다. system-uuid는 @GenericGenerator의 이름이다
	@GeneratedValue(generator="system-uuid")
	//@GenericGenerator
	//Hibernate가 제공하는 기본 Generator가 아닌 나만의 Generator를 사용하고 싶을 때 사용한다.
	@GenericGenerator(name="system-uuid", strategy="uuid")
	private String id; //이 객체의 id
	private String userId;//이 객체를 생성한 유저의 아이디
	private String title;//Todo 타이틀 예)운동 하기
	private boolean done;//true - todo를 완료한 경우(checked)
	
	
	
}
