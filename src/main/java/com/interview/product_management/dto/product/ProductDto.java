package com.interview.product_management.dto.product;

import com.interview.product_management.enums.product.ProductStatus;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductDto(
        Long id,

        @NotBlank(message = "Title cannot be Blank!")
        String name,

        @NotNull(message = "Price cannot be null!")
        @Digits(integer = 10, fraction = 2, message = "Price cannot have more than 2 decimal and value cannot be more than 10 digit integer")
        @Positive(message = "Price cannot be negative.")
        BigDecimal price,

        @NotNull(message = "Quantity cannot be null")
        @Positive(message = "Quantity cannot be negative.")
        Integer quantity,

        ProductStatus productStatus
) {
    public ProductDto(Long id, String name, BigDecimal price, Integer quantity){
        this(id, name, price, quantity, null);
    }
}
