package com.interview.product_management.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CartProductDetailsDto(
        @NotBlank(message = "Product name cannot be Blank!")
        String productName,

        @NotNull(message = "Product quantity cannot be null!")
        Integer quantity
) {
}
