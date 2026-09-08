package com.interview.product_management.dto.product;

import jakarta.validation.constraints.NotNull;

public record CartItemsDto(
        Long id,

        @NotNull(message = "CartId cannot be null!")
        Long cartId,

        @NotNull(message = "ProductId cannot be null!")
        Long productId,

        @NotNull(message = "Quantity cannot be null!")
        Integer quantity
){

}