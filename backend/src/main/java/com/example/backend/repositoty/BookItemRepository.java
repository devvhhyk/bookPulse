package com.example.backend.repositoty;

import com.example.backend.entity.BookItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookItemRepository extends JpaRepository<BookItem, Integer> {
    List<BookItem> findByIdIn(List<Integer> ids);
}
