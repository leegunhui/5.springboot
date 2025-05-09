package com.korea.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.korea.todo.dto.UserDTO;
import com.korea.todo.model.ResponseDTO;
import com.korea.todo.model.UserEntity;
import com.korea.todo.service.UserService;

import lombok.extern.slf4j.Slf4j;
import security.TokenProvider;

@Slf4j // Lombok을 사용하여 로그를 남길 수 있는 log 객체를 자동으로 생성한다.
@RestController // 이 클래스가 RESTful 웹서비스의 컨트롤러 역할을 한다는 것을 나타낸다.
@RequestMapping("/auth") // 이 컨트롤러의 기본 URI 경로가 "/auth"로 설정된다.
public class UserController {

    @Autowired // UserService 타입의 빈을 자동으로 주입받는다.
    private UserService userService; // 사용자 관련 비즈니스 로직을 처리하는 서비스 클래스.
    
    @Autowired
	private TokenProvider tokenProvider;
    
    @PostMapping("/signup") // POST 요청을 처리하는 메서드로, 경로는 "/auth/signup"이다.
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) { 
        // 요청 본문에 포함된 UserDTO 객체를 수신하여 처리한다.
        try {
            // UserDTO를 기반으로 UserEntity 객체를 생성한다.
            UserEntity user = UserEntity.builder()
                            .username(userDTO.getUsername()) // UserDTO에서 username 값을 가져온다.
                            .password(userDTO.getPassword()) // UserDTO에서 password 값을 가져온다.
                            .build(); // UserEntity 객체를 빌드한다.

            // UserService를 이용해 새로 만든 UserEntity를 데이터베이스에 저장한다.
            UserEntity registeredUser = userService.create(user);

            // 등록된 UserEntity 정보를 UserDTO로 변환하여 응답에 사용한다.
            UserDTO responseUserDTO = UserDTO.builder()
                            .id(registeredUser.getId()) // 등록된 유저의 ID를 가져온다.
                            .username(registeredUser.getUsername()) // 등록된 유저의 username을 가져온다.
                            .build(); // UserDTO 객체를 빌드한다.

            // 성공적으로 저장된 유저 정보를 포함한 HTTP 200 응답을 반환한다.
            return ResponseEntity.ok(responseUserDTO);
        } catch (Exception e) {
            // 예외가 발생한 경우, 에러 메시지를 포함한 ResponseDTO 객체를 만들어서 응답한다.
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();

            // HTTP 400 상태 코드를 반환하고, 에러 메시지를 응답 본문에 포함시킨다.
            return ResponseEntity
                            .badRequest() // HTTP 400 응답을 생성한다.
                            .body(responseDTO); // 에러 메시지를 포함한 응답 본문을 반환한다.
        }
    }

    @PostMapping("/signin") // POST 요청을 처리하는 메서드로, 경로는 "/auth/signin"이다.
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {
        // 요청 본문으로 전달된 UserDTO의 username과 password를 기반으로 유저를 조회한다.
        UserEntity user = userService.getByCredentials(
                        userDTO.getUsername(), // UserDTO에서 username 값을 가져온다.
                        userDTO.getPassword()); // UserDTO에서 password 값을 가져온다.

        // 사용자가 존재하면
        if (user != null) {
        	//토큰생성
        	final String token = tokenProvider.create(user);
            // 인증에 성공한 경우 유저 정보를 UserDTO로 변환하여 응답에 사용한다.
            final UserDTO responseUserDTO = UserDTO.builder()
                            .id(user.getId()) // 유저의 ID를 UserDTO에 설정한다.
                            .username(user.getUsername())
                            .token(token)
                            .build(); // UserDTO 객체를 빌드한다.

            // 성공적으로 인증된 유저 정보를 포함한 HTTP 200 응답을 반환한다.
            return ResponseEntity.ok().body(responseUserDTO);
        } else {
            // 유저가 존재하지 않거나 인증 실패 시 에러 메시지를 포함한 ResponseDTO를 반환한다.
            ResponseDTO responseDTO = ResponseDTO.builder()
                            .error("Login failed.") // 에러 메시지를 설정한다.
                            .build(); // ResponseDTO 객체를 빌드한다.

            // HTTP 400 상태 코드를 반환하고, 에러 메시지를 응답 본문에 포함시킨다.
            return ResponseEntity
                            .badRequest() // HTTP 400 응답을 생성한다.
                            .body(responseDTO); // 에러 메시지를 포함한 응답 본문을 반환한다.
        }
    }
}