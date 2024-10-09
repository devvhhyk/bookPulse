package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "books")
public class BookItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100, nullable = false)
    private String title; // 도서 제목

    @Column(length = 100, nullable = false)
    private String author; // 저자

    @Column(length = 200)
    private String publisher; // 출판사

    @Column(length = 500)
    private String description; // 도서 설명

    @Column
    private int price; // 가격

    @Column(length = 100)
    private String imgPath; // 이미지 경로

    public void setQuantity(int quantity) {
    }
}
