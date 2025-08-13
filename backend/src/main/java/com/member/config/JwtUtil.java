package com.member.config;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "mysecretkeymysecretkeymysecretkey1234"; // 최소 256bit
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1시간

    private final Key key;

    public JwtUtil() {
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());  // 시크릿 키를 Key 객체로 변환
    }

    // 토큰 생성
    public String createToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        System.out.println("토큰 발급 시간: " + now);
        System.out.println("토큰 만료 시간: " + expiryDate);

        return Jwts.builder()
                .setSubject(username) // 사용자 식별 정보
                .setIssuedAt(now) // 발급 시간
                .setExpiration(expiryDate)   // 만료 시간
                .signWith(key, SignatureAlgorithm.HS256)  // 서명 알고리즘 + 비밀키
                .compact();    // 최종 JWT 문자열 생성
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token); // 토큰 파싱 시 예외가 없으면 유효
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false; // 유효하지 않은 토큰
        }
    }

    // 토큰에서 사용자 이름 추출
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)// 토큰 해석
                .getBody()
                .getSubject();
    }


    // JWT 토큰 추출 메서드 (요청 헤더에서 "Bearer {토큰}" 꺼냄)
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 제거
        }
        return null;
    }
}
