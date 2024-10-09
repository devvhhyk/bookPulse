package com.example.backend.repositoty;

import com.example.backend.entity.BookCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookCartRepository extends JpaRepository<BookCart, Integer> {
    List<BookCart> findByMemberId(int memberId);

    BookCart findByMemberIdAndItemId(int memberId, int itemId);

    void deleteByMemberIdAndItemId(int memberId, int itemId);

    void deleteByMemberId(int memberId);
}
