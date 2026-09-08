package com.interview.product_management.controller;

import com.interview.product_management.dto.product.CartDto;
import com.interview.product_management.dto.product.CartItemsDto;
import com.interview.product_management.dto.product.OrderDetailsDto;
import com.interview.product_management.dto.product.OrderDto;
import com.interview.product_management.enums.orders.PaymentMode;
import com.interview.product_management.enums.orders.PaymentStatus;
import com.interview.product_management.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/api/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping("/{id}")
    public ResponseEntity<CartDto> getCartItems(@PathVariable("id") Long cartId) {
        CartDto cartItems = cartService.getCartItems(cartId);
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }

    @PostMapping("/{id}/{productId}")
    public ResponseEntity<Void> addToCart(@PathVariable("id") Long cartId, @PathVariable Long productId) {
        cartService.addItemToCart(cartId, productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}/{productId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable("id") Long cartId, @PathVariable Long productId) {
        cartService.removeItemFromCart(cartId, productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/checkout")
    public ResponseEntity<OrderDetailsDto> checkout(@Valid @RequestBody OrderDto orderDto) {
        OrderDetailsDto orderDetails = cartService.checkout(orderDto);
        return  new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }

}
