package com.interview.product_management.dto.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CartItemsDto(
        @NotNull(message = "CartItems id cannot be null!")
        Long id,

        @NotNull(message = "ProductId cannot be null!")
        Long productId,

        @NotNull(message = "Quantity cannot be null!")
        @Positive(message = "Quantity cannot be negative!")
        Integer quantity
){
        public CartItemsDto(
                @NotNull(message = "ProductId cannot be null!")
                Long productId,

                @NotNull(message = "Quantity cannot be null!")
                @Positive(message = "Quantity cannot be negative!")
                Integer quantity
        ){
                this(0L, productId, quantity);
        }
}