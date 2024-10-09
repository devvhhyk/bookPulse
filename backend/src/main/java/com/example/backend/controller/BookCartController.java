package com.example.backend.controller;

import com.example.backend.dto.UpdateQuantityDTO;
import com.example.backend.entity.BookCart;
import com.example.backend.entity.BookItem;
import com.example.backend.repositoty.BookCartRepository;
import com.example.backend.repositoty.BookItemRepository;
import com.example.backend.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class BookCartController {

    @Autowired
    JwtService jwtService;

    @Autowired
    BookCartRepository cartRepository;

    @Autowired
    BookItemRepository itemRepository;

    // 장바구니 상품 전체 조회
    @GetMapping("/api/cart/items")
    public ResponseEntity<List<BookItem>> getCartItems(@CookieValue(value = "token", required = false) String token) {
        if (!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        int memberId = jwtService.getId(token);
        List<BookCart> carts = cartRepository.findByMemberId(memberId);

        if (carts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<Integer> itemIds = carts.stream().map(BookCart::getItemId).toList();
        List<BookItem> items = itemRepository.findByIdIn(itemIds);

        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    // 장바구니에 상품 추가 및 수량 변경
    @PostMapping("/api/cart/items/{itemId}")
    public ResponseEntity pushCartItem (
            @PathVariable("itemId") int itemId,
            @RequestParam(value = "quantity", defaultValue = "1") int quantity,
            @CookieValue(value = "token", required = false) String token
    ) {
        if(!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        // JWt 토콘에서 사용자 ID를 추출
        int memberId = jwtService.getId(token);

        // 해당 사용자의 장바구니에서 해당 상품이 이미 존재하는지 확인
        BookCart cart = cartRepository.findByMemberIdAndItemId(memberId, itemId);

        // 해당 상품이 장바구니에 없으면, 새로운 항목을 생성하여 저장
        if(cart == null) {
            BookCart newCart = new BookCart();
            newCart.setMemberId(memberId);
            newCart.setItemId(itemId);
            newCart.setQuantity(quantity); // 기본 수량 추가
            cartRepository.save(newCart); // 새로운 장바구니 항목을 데이터베이스에 저장
        } else {
            // 이미 장바구니에 있으면 수량 증가
            cart.setQuantity(cart.getQuantity() + quantity);
            cartRepository.save(cart);
        }

        return new ResponseEntity<>("상품이 장바구니에 추가되었습니다.", HttpStatus.OK);
    }

    // 장바구니에서 상품 삭제
    @DeleteMapping("/api/cart/items/{itemId}")
    public ResponseEntity<String> removeCartItem(
            @PathVariable("itemId") int itemId,
            @CookieValue(value = "token", required = false) String token
    ) {
        // JWT 토큰 검증
        if (!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다.");
        }

        // 토큰에서 사용자 ID 추출
        int memberId = jwtService.getId(token);
        System.out.println("Member ID: " + memberId + ", Item ID: " + itemId);

        // 장바구니 항목 찾기
        BookCart cart = cartRepository.findByMemberIdAndItemId(memberId, itemId);

        // 장바구니 항목이 존재하는지 확인
        if (cart != null) {
            cartRepository.delete(cart); // 장바구니 항목 삭제
            System.out.println("장바구니 항목이 삭제되었습니다.");
            return new ResponseEntity<>("항목이 삭제되었습니다.", HttpStatus.OK);
        } else {
            System.out.println("장바구니 항목을 찾을 수 없습니다. memberId: " + memberId + ", itemId: " + itemId);
            return new ResponseEntity<>("항목을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }
    }


}