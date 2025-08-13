package com.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity  // JPA 엔티티 클래스
@Table(name = "users") // 매핑할 테이블명
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder  // 빌더 패턴 생성
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가
    private Long id;

    @Column(unique = true, nullable = false) // 유니크 + null 불가
    private String username;

    @Column(nullable = false)  // null 불가
    private String password;
}
