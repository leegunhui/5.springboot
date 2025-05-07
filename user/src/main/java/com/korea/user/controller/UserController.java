package com.korea.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.korea.user.dto.UserDTO;
import com.korea.user.model.UserEntity;
import com.korea.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	
	// 사용자 생성
	@PostMapping
	public ResponseEntity<List<UserDTO>> createUser(@RequestBody UserDTO dto){
		UserEntity entity = UserDTO.toEntity(dto);
		List<UserDTO> users = userService.create(entity);
		return ResponseEntity.ok(users);
	}
	
	// 모든 사용자 조회
	@GetMapping
	public ResponseEntity<List<UserDTO>> getAllUsers() {
	    List<UserDTO> users = userService.getAllUsers();
	    return ResponseEntity.ok(users);
	}
	
	// 이메일로 사용자 검색
	@GetMapping("/{email}")
	public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
	    UserDTO user = userService.getUserByEmail(email);
	    return ResponseEntity.ok(user);
	}
	
	// 사용자 정보 수정 (ID 기반)
	@PutMapping
	public ResponseEntity<?> updateUser(@RequestBody UserDTO dto) {
	    UserEntity entity = UserDTO.toEntity(dto);
	    List<UserDTO> updatedUser = userService.updateUser(entity);

	    return ResponseEntity.ok(updatedUser);
	    }
	
	// 사용자 삭제 (ID 기반)
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable int id) {
	    boolean isDeleted = userService.deleteUser(id);
	    if (isDeleted) {
	        return ResponseEntity.ok("User deleted successfully");
	    } else {
	        return ResponseEntity.status(404).body("User not found with id " + id);
	    }
	}
}
