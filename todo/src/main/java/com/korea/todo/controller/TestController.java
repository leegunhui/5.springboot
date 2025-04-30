package com.korea.todo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.korea.todo.dto.TestRequestBodyDTO;
import com.korea.todo.model.ResponseDTO;

@RestController
//데이터를 반환하는 컨트롤러로 사용
//JSON이나 XML형식의 데이터를 반환한다
//@Controller, @ResponseBody 두개의 어노테이션의 결합이다
//@ResponseBody 는 메서드의 반환값을 HTTP ResponseBody로 직렬화해 클라이언트에게 전달한다.
@RequestMapping("test") // test 주소로 요청이 들어왔을때 현재 컨트롤러로 들어올 수 있게 해준다
public class TestController {

	// @GetMapping get으로 요청이 들어왔을때 요청을 받아서 아래 메서드 실행
	@GetMapping("/testGetMapping")
	public String testController() {
		return "Hello World";
	}

	// localhost:10000/users/1
	@GetMapping("/users/{id}") // {id}가 경로변수로 동작한다.
	// @PathVariable("id")는 {id} 부분을 받아서 메서드의 매개변수 userId에 할당한다.
	// 예를 들어, GET /users/5 요청을 보내면, userId에 5가 할당되고 "User ID: 5"라는 응답을 반환한다.
	public String getUserById(@PathVariable("id") Long userId) {
		return "User ID: " + userId;
	}

	@GetMapping("/users/{userId}/orders/{orderId}")
	public String getOrderByUserAndOrderId(@PathVariable("userId") Long userId, @PathVariable("orderId") Long orderId) {
		return "User ID: " + userId + ", Order ID: " + orderId;
	}

	@GetMapping("/users")
	// public String getUserById(@RequestParam Long id)
	// 쿼리 스트링의 key와 매개변수의 변수명이 일치한다면 value값을 안줘도 된다
	// @RequestParam(required=false Long id)
	// 값을 필수로 넣지 않아도 에러가 나지는 않는다
	// public String getUserById(@RequestParam(defaultValue="0" Long id)
	// 값이 넘어오지 않을 때 기본값을 설정할 수 있다
	public String getUserById2(@RequestParam("id") Long userId) {
		return "User ID : " + userId;
	}

	@GetMapping("/search")
	public String search(@RequestParam("query") String query, @RequestParam("page") int page) {
		return "Search query: " + query + ", Page: " + page;
	}

	@PostMapping("/submitForm")
	public String submitForm(@RequestParam("name") String name, @RequestParam("email") String email) {
		return "Form submitted: Name = " + name + ", Email = " + email;
	}

	@GetMapping("/testRequestBody")
	public String testRequestBody(@RequestBody TestRequestBodyDTO dto) {
		return "ID : " + dto.getId() + ",Message : " + dto.getMessage();
	}
	
	@GetMapping("/testResponseBody")
	public ResponseDTO<String> testResponseBody(){
		List<String> list = new ArrayList<>();
		list.add("Hellow World! I'm ResponseDTO");
		ResponseDTO<String>response = ResponseDTO.<String>builder().data(list).build();
		return response;
	}
	
	@GetMapping("/testResponseEntity")
	public ResponseEntity<?> testControllerResponseEntity(){
		List<String> list = new ArrayList<>();
		list.add("Hellow World! I'm ResponseEntity. And you got 400");
		ResponseDTO<String>response = ResponseDTO.<String>builder().data(list).build();
		//http status를 400으로 설정
		return ResponseEntity.badRequest().body(response);
	}
}