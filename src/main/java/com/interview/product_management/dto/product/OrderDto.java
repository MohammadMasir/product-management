package com.interview.product_management.dto.product;

import com.interview.product_management.enums.orders.PaymentMode;
import com.interview.product_management.enums.orders.PaymentStatus;
import jakarta.validation.constraints.NotNull;

public record OrderDto(
        @NotNull(message = "CartId cannot be null!")
        Long cartId,

        @NotNull(message = "PaymentMode cannot be null!")
        PaymentMode paymentMode,

        @NotNull(message = "PaymentStatus cannot be null!")
        PaymentStatus paymentStatus
) {
}
