package com.interview.product_management.dto.product;

import com.interview.product_management.enums.orders.DeliveryStatus;
import com.interview.product_management.enums.orders.PaymentMode;
import com.interview.product_management.enums.orders.PaymentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record OrderDetailsDto(
        @NotNull(message = "OrderId cannot be null!")
        Long id,

        List<CartProductDetailsDto> cartProductDetailsDtoList,

        @NotNull(message = "Items count cannot be null!")
        Integer itemCount,

        @NotNull(message = "Total count cannot be null!")
        BigDecimal totalAmount,

        @NotNull(message = "Payment mode cannot be null!")
        PaymentMode paymentMode,

        @NotNull(message = "Delivery Status cannot be null!")
        DeliveryStatus deliveryStatus,

        @NotNull(message = "Payment Status cannot be null!")
        PaymentStatus paymentStatus
) {
}
