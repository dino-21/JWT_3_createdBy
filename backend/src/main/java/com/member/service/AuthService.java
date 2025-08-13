package com.member.service;

import com.member.config.JwtUtil;
import com.member.dto.LoginRequest;
import com.member.dto.SignupRequest;
import com.member.entity.User;
import com.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service // 서비스 빈 등록
@RequiredArgsConstructor  // 생성자 자동 주입 (final 필드 대상)
public class AuthService {
    private final UserRepository userRepository; // 사용자 조회용
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화/검증

    private final JwtUtil jwtUtil; // 추가

    // 로그인 처리 메서드
    public String login(LoginRequest request) {
        // 사용자 조회 (없으면 예외 발생)
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다."));

        // 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 틀렸습니다.");
        }


        // return "로그인 성공"; // 성공 메시지 반환

        // 3. JWT 토큰 생성 후 반환
        return jwtUtil.createToken(user.getUsername());
    }

    // 회원가입 처리 메서드
    public void signup(SignupRequest request) {
        // 1. 동일한 username이 이미 DB에 존재하는지 확인
        Optional<User> existingUser = userRepository.findByUsername(request.getUsername());
        if (existingUser.isPresent()) {
            // 이미 존재하는 경우 예외 발생 (회원가입 실패 처리)
            throw new RuntimeException("이미 존재하는 사용자입니다.");
        }

        // 2. 비밀번호를 Spring Security의 PasswordEncoder로 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 3. 새로운 User 엔티티 생성 후 입력값 설정
        User user = new User();
        user.setUsername(request.getUsername()); // 사용자명 설정
        user.setPassword(encodedPassword); // 암호화된 비밀번호 설정

        // 4. 사용자 정보를 DB에 저장
        userRepository.save(user);
    }

}
