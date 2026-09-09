package com.interview.product_management.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "cart_items")
@DynamicInsert
public class CartItems {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_item", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @Column(nullable = false, columnDefinition = "INTEGER check (quantity > 0)") // While "@PreUpdate"ing we'll check if during deduction the quantity is <= 0 then we'll remove the entire row OR during insertion to update the newQuantity.
    private Integer quantity = 1;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // TODO : write the @PreUpdate for cart-item on quantity on each quantity update for insertion OR deduction (until we remove the entire row when quantity = 0)..
}
