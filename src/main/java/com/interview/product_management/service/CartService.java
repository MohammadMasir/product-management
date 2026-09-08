package com.interview.product_management.service;

import com.interview.product_management.dto.product.CartItemsDto;
import com.interview.product_management.dto.product.OrderDetailsDto;
import com.interview.product_management.repository.CartItemsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CartService {

    private final CartItemsRepository cartItemsRepository;
    private final OrderService orderService;

    public void addItemToCart(Long cartId, Long productId) {

    }

    public void removeItemFromCart(Long cartId, Long productId) {

    }

    public List<CartItemsDto> getCartItems(Long cartId) {
        return null;
    }

    public OrderDetailsDto checkout(Long cartId) {
        return null;
    }

}
