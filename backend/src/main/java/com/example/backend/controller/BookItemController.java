package com.example.backend.controller;

import com.example.backend.entity.BookItem;
import com.example.backend.repositoty.BookItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookItemController {

    @Autowired
    BookItemRepository itemRepository;

    // 모든 도서를 조회
    @GetMapping("/api/books")
    public List<BookItem> getBooks() {
        List<BookItem> books = itemRepository.findAll();
        return books;
    }
}