package com.member.service;

import com.member.dto.LoginRequest;
import com.member.entity.User;
import com.member.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest  // 스프링 부트 통합 테스트 실행
class AuthServiceTest {
    @Autowired
    private AuthService authService; // 로그인 처리 서비스

    @Autowired
    private UserRepository userRepository; // 사용자 DB 접근용 JPA 인터페이스

    @Autowired
    private PasswordEncoder passwordEncoder; // 비밀번호 암호화/검증 도구

    @Test
    @DisplayName("손오공 로그인 성공") // 테스트 이름
    void loginTest() {
        // 먼저 DB에 테스트용 유저를 넣는다
        User user = new User();
        user.setUsername("손오공");
        user.setPassword(passwordEncoder.encode("1234"));  // 비밀번호는 암호화
        userRepository.save(user);  // 저장

        // 로그인 요청 생성
        LoginRequest request = new LoginRequest();
        request.setUsername("손오공");
        request.setPassword("1234");

        // 로그인 시도 및 결과 검증
        String result = authService.login(request);

        assertEquals("로그인 성공", result);
    }
}