package com.korea.book.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
public class NaverBookApiController {

    // WebClient 객체를 사용하여 외부 API와 비동기 통신을 하기 위한 필드 선언
    private final WebClient webClient;

    // application.properties 또는 application.yml 파일에서 네이버 API 클라이언트 ID 값을 가져옴
    @Value("${naver.client.id}")
    private String clientId;

    // application.properties 또는 application.yml 파일에서 네이버 API 클라이언트 Secret 값을 가져옴
    @Value("${naver.client.secret}")
    private String clientSecret;

    // 생성자를 통해 WebClient.Builder를 주입받고 WebClient 객체를 초기화
    // 네이버 API의 기본 URL을 설정
    public NaverBookApiController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://openapi.naver.com/v1/search").build();
    }

    // 책 검색 요청을 처리하는 GET API 엔드포인트
    @GetMapping("/api/books")
    public Mono<String> searchBooks(@RequestParam String query) {
        // WebClient를 사용하여 네이버 책 검색 API에 GET 요청을 보냄
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/book.json")
                        // 검색어를 query 매개변수로 추가
                        .queryParam("query", query)
                        .build())
                // 요청 헤더에 Content-Type을 JSON으로 설정
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                // 네이버 API 인증을 위한 Client ID와 Secret을 헤더에 추가
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                // 요청을 서버로 보내고 응답을 받음
                .retrieve() // API 요청을 보내고 응답을 기다림
                // 응답의 Body를 Mono<String>으로 변환하여 비동기로 처리
                .bodyToMono(String.class); // 응답 데이터를 Mono 객체로 받음 (JSON 데이터를 문자열로 처리)
    }
}