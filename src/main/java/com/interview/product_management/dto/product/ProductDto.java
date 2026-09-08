package com.interview.product_management.dto.product;

import com.interview.product_management.enums.product.ProductStatus;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductDto(
        @NotNull(message = "Id cannot be null!")
        Long id,

        @NotBlank(message = "Title cannot be Blank!")
        String name,

        @NotNull(message = "Price cannot be null!")
        @Digits(integer = 10, fraction = 2, message = "Price cannot have more than 2 decimal and value cannot be more than 10 digit integer")
        BigDecimal price,

        @NotNull(message = "Quantity cannot be null")
        Integer quantity,

        ProductStatus productStatus
) {
    public ProductDto(String name, BigDecimal price, Integer quantity, ProductStatus productStatus){
        this(0L, name, price, quantity, productStatus);
    }
    public ProductDto(Long id, String name, BigDecimal price, Integer quantity){
        this(id, name, price, quantity, null);
    }
}
