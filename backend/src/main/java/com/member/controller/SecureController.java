package com.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/secure")
public class SecureController {
    // JWT 인증된 사용자만 접근 가능한 테스트용 API
    @GetMapping("/hello")
    public String hello() {
        return "JWT 인증된 사용자만 접근 가능!";
    }
}
