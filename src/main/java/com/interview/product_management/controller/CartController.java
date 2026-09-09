package com.interview.product_management.controller;

import com.interview.product_management.dto.product.CartDto;
import com.interview.product_management.dto.product.CartItemsDto;
import com.interview.product_management.dto.product.OrderDetailsDto;
import com.interview.product_management.dto.product.OrderDto;
import com.interview.product_management.model.User;
import com.interview.product_management.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/api/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartDto> getCartItems(
            @AuthenticationPrincipal User user
    ) {
        CartDto cartItems = cartService.getCartItemsById(user);
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> addToCart(
            @PathVariable("id") Long productId,
            @AuthenticationPrincipal User user
            ) {
        cartService.addItemToCart(productId, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<Void> updateCartQuantity(
            @Valid @RequestBody CartItemsDto cartItems,
            @AuthenticationPrincipal User user
    ) {
        cartService.updateCartQuantity(cartItems, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeCartItemByProductId(
            @PathVariable("id") Long productId,
            @AuthenticationPrincipal User user){
        cartService.deleteCartItemByProductId(productId, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/checkout")
    public ResponseEntity<OrderDetailsDto> checkout(
            @Valid @RequestBody OrderDto orderDto,
            @AuthenticationPrincipal User user
    ) {
        OrderDetailsDto orderDetails = cartService.checkout(orderDto, user);
        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }

}
