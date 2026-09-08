package com.interview.product_management.model;

import com.interview.product_management.enums.product.ProductStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "products")
@DynamicInsert
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, scale = 2, precision = 10)
    private BigDecimal price;

    @Column(nullable = false, columnDefinition = "INTEGER check (quantity > 0)")
    private Integer quantity;

    @Column(nullable = false, columnDefinition = "VARCHAR(50) DEFAULT 'DISABLE'")
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<CartItems> cartItems = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // TODO : write the Product quantity updation using @PreUpdate

}
