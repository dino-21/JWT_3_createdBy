package com.member.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String username; //클라이언트가 보내는 JSON 형식과 필드 이름이 정확히 일치
    private String password;  //{ "username": "손오공","password": "1234"}
}
