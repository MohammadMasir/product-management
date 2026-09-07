package com.interview.product_management.model;

import com.interview.product_management.enums.orders.DeliveryStatus;
import com.interview.product_management.enums.orders.PaymentMode;
import com.interview.product_management.enums.orders.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "orders")
@DynamicInsert
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "item_count", nullable = false, columnDefinition = "INTEGER check (itemCount > 0)")
    private Integer itemCount;

    @Column(name = "total_quantity", nullable = false, columnDefinition = "INTEGER check (totalQuantity > 0)")
    private Integer totalQuantity;

    @Column(name = "total_amount", nullable = false, columnDefinition = "NUMERIC(10, 2) check (totalAmount > 0)")
    private BigDecimal totalAmount;

    @Column(name = "payment_mode", nullable = false, columnDefinition = "VARCHAR(50) DEFAULT 'NONE'")
    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;

    @Column(name = "delivery_status", nullable = false, columnDefinition = "VARCHAR(50) DEFAULT 'PROCESSING'")
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @Column(name = "payment_status", nullable = false, columnDefinition = "VARCHAR(50) DEFAULT 'PENDING'")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users", nullable = false)
    private User users;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}

