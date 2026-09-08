package com.interview.product_management.dto;

import java.math.BigDecimal;

public interface PriceQuantityProjection {
    BigDecimal getPrice();
    Integer getQuantity();
}
