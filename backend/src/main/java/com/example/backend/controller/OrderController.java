package com.example.backend.controller;

import com.example.backend.dto.OrderDTO;
import com.example.backend.entity.Order;
import com.example.backend.repositoty.BookCartRepository;
import com.example.backend.repositoty.OrderRepository;
import com.example.backend.service.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    BookCartRepository cartRepository;

    @Autowired
    JwtService jwtService;

    @Autowired
    OrderRepository orderRepository;

    // 사용자의 주문 목록을 조회하는 API
    @GetMapping("/api/orders")
    public ResponseEntity getOrder (
            @CookieValue(value = "token", required = false) String token
    ) {

        if (!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        int memberId = jwtService.getId(token);

        // 사용자 ID로 주문 목록을 조회, 최신순으로 정렬
        List<Order> orders = orderRepository.findByMemberIdOrderByIdDesc(memberId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // 사용자가 주문을 생성하는 API
    @Transactional // 트랜잭션이 필요한 메소드임을 나타냄. 여러 데이터 변경 작업이 한 번에 처리됨
    @PostMapping("/api/orders")
    public ResponseEntity pushOrder (
            @RequestBody OrderDTO orderDTO,
            @CookieValue(value = "token", required = false) String token
    ) {

        if(!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        int memberId = jwtService.getId(token);
        Order order = new Order(); // 새로운 주문 객체 생성

        // 주문 객체에 사용자 ID 및 주문 정보 설정
        order.setMemberId(memberId);
        order.setName(orderDTO.getName());
        order.setAddress(orderDTO.getAddress());
        order.setPayment(orderDTO.getPayment());
        order.setCardNumber(orderDTO.getCardNumber());
        order.setItems(orderDTO.getItems());

        // 주문 정보를 데이터베이스에 저장
        orderRepository.save(order);

        // 주문이 완료되면 사용자의 장바구니 항목을 삭제
        cartRepository.deleteByMemberId(memberId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}