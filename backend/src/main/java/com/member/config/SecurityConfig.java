package com.member.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration  // 설정 클래스임을 명시
@RequiredArgsConstructor  // final 필드를 생성자 주입 받음
public class SecurityConfig {

    private final JwtUtil jwtUtil; // JWT 토큰을 만들고 검사하는 유틸 클래스

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 암호화에 사용
    }



    // JWT 필터 빈 등록
    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        // 만든 JwtAuthFilter 객체를 빈으로 등록
        return new JwtAuthFilter(jwtUtil);
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())  // CSRF 비활성화 (API 서버에서 보통 사용 안 함)
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/auth/login").permitAll()  //로그인 경로는 인증 없이 허용
//                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
//                );
//
//        return http.build(); // 보안 필터 체인 생성
//    }


    // 스프링 시큐리티의 보안 설정을 정의하는 필터 체인
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // CSRF 보안 기능 끔
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()       // 로그인, 회원가입은 누구나 접근 가능
                        .requestMatchers("/api/members/**").permitAll()    // 회원 목록도 접근 허용
                        .requestMatchers("/member/list").permitAll()  //추가
                        .requestMatchers("/api/secure/**").authenticated() // 인증된 유저만 접근 가능
                        .requestMatchers("/api/auth/**").permitAll()  // 로그인/회원가입 허용
                        .anyRequest().authenticated()                           // 나머지 요청은 인증 필요
                )
                // 요청이 들어오면 먼저 JWT 필터를 실행해서 토큰 확인
                .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();  // 설정 완료된 보안 필터 체인 반환
    }

    // 인증 관련 설정 (로그인 시 사용자 정보 확인할 때 사용됨) ,인증 관리자 (AuthenticationManager) 빈 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();   // 인증 매니저 반환
    }
}
