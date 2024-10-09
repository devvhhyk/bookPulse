package com.example.backend.controller;

import com.example.backend.entity.Member;
import com.example.backend.repositoty.MemberRepository;
import com.example.backend.service.JwtService;
import com.example.backend.service.JwtServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AccountController {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    JwtService jwtService;

    // 회원가입
    @PostMapping("/api/account/register")
    public ResponseEntity register(@RequestBody Map<String, String> params) {
        // 이메일 중복체크
        if(memberRepository.findByEmail(params.get("email")) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다.");
        }

        // 회원 정보 저장
        Member member = new Member();
        member.setName(params.get("name"));
        member.setEmail(params.get("email"));
        member.setPassword(params.get("password"));
        member.setPhoneNumber(params.get("phoneNumber"));

        memberRepository.save(member);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 이메일 중복 확인 API
    @PostMapping("/api/account/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestBody Map<String, String> params) {
        String email = params.get("email");

        // 이메일로 멤버를 조회
        Member member = memberRepository.findByEmail(email);

        // 이메일이 이미 존재하면 true, 아니면 false
        boolean exists = member != null;

        // 결과를 클라이언트로 반환
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 로그인
    @PostMapping("/api/account/login")
    public ResponseEntity login(@RequestBody Map<String, String> params,
                                HttpServletResponse res) {
        Member member = memberRepository.findByEmailAndPassword(params.get("email"), params.get("password"));

        if (member != null) {
            int id = member.getId();
            String token = jwtService.getToken("id", id);

            Cookie cookie = new Cookie("token", token); // JWT를 쿠키에 저장
            cookie.setHttpOnly(true); // 자바스크립트에서 접근 불가 설정
            cookie.setPath("/");

            res.addCookie(cookie);

            return new ResponseEntity<>(id, HttpStatus.OK);
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    // 로그아웃
    @PostMapping("/api/account/logout")
    public ResponseEntity logout(HttpServletResponse res) {
        Cookie cookie = new Cookie("token", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        res.addCookie(cookie);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 로그인 상태 확인
    @GetMapping("/api/account/check")
    public ResponseEntity check(@CookieValue(value = "token", required = false) String token) {
        Claims claims = jwtService.getClams(token); // JWT에서 회원 정보 추출

        if(claims != null) {
            int id = Integer.parseInt(claims.get("id").toString()); // 추출한 회원 ID 반환
            return new ResponseEntity<>(id, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}