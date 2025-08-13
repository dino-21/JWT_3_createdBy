package com.member.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 로그인 시 클라이언트가 보내는 ID/PW 정보를 담는 DTO
 */
@Getter
@Setter
@Data
public class LoginRequest {
    private String username; // 로그인할 때 사용하는 사용자 이름(ID)
    private String password;  // 로그인할 때 입력하는 비밀번호
}
