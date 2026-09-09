package com.interview.product_management.dto.product;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record CartDto(
        @NotNull(message = "Cart items cannot be null!")
        @Valid List<CartItemsDto> cartItems,

//        @NotNull(message = "Total amount cannot be null!")
        BigDecimal totalAmount
) {
}
