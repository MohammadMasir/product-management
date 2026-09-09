package com.interview.product_management.repository;

import com.interview.product_management.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUsers_Id(Long usersId);
}
