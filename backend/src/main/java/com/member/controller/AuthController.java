package com.member.controller;

import com.member.dto.LoginRequest;
import com.member.dto.SignupRequest;
import com.member.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController   // REST API 컨트롤러
@RequestMapping("/api/auth")   // 기본 URL 경로 설정
@RequiredArgsConstructor  // 생성자 주입 자동 생성
public class AuthController {
    private final AuthService authService; // 로그인 서비스

    // 공백/대소문자 정규화 유틸
    private String norm(String s) {
        return s == null ? null : s.trim().toLowerCase();
    }
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
//        try {
//            String result = authService.login(request);
//            return ResponseEntity.ok(result); // 200 OK
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage()); // 401
//        }
//    }

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
//        String token = authService.login(request);
//        return ResponseEntity.ok()
//                .header("Authorization", "Bearer " + token) // 헤더에 JWT 추가
//                .body("로그인 성공");
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String token = authService.login(request);

        // JSON 형태로 반환 (key: "token")
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        try {
            // username  문자공백 정규화
            request.setUsername(norm(request.getUsername()));

            // 1. 회원가입 처리
            authService.signup(request);

            // 2. 성공 시 200 OK 응답과 메시지 반환
            return ResponseEntity.ok("회원가입이 완료되었습니다.");

        } catch (RuntimeException e) {
            // 3. 예외 발생 시 400 Bad Request 상태와 오류 메시지 반환
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
