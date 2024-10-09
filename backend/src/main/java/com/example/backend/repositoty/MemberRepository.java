package com.example.backend.repositoty;

import com.example.backend.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Member findByEmail(String email); // 이메일 중복체크
    Member findByEmailAndPassword(String email, String password);
}
