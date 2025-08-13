package com.member.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

// JWT 인증 필터 (모든 요청 전에 실행됨)
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    // JwtUtil 주입 (토큰 검증용)
    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // 실제 필터 처리 로직
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 요청 헤더에서 토큰 꺼냄
        String token = resolveToken(request);

        // 토큰이 있고 유효한 경우
        if (token != null && jwtUtil.validateToken(token)) {
            // 토큰에서 사용자 이름 꺼냄
            String username = jwtUtil.getUsernameFromToken(token);

            // 인증 객체 생성 (비밀번호는 null, 권한은 빈 리스트)
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());

            // 요청 정보 넣기
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Spring Security에 인증된 사용자 등록
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 다음 필터로 넘기기
        filterChain.doFilter(request, response);
    }

    // 요청 헤더에서 "Bearer {토큰}" 형태의 토큰 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        // 헤더가 있고 "Bearer "로 시작하면 토큰만 잘라서 반환
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 이후의 토큰만 추출
        }

        return null; // 없으면 null 반환
    }
}
