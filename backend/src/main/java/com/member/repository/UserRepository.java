package com.member.repository;

import com.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 사용자 조회를 위한 JPA 인터페이스
 */
public interface UserRepository extends JpaRepository<User, Long> {
    // username으로 사용자 조회 (로그인 시 사용)
    Optional<User> findByUsername(String username);
}