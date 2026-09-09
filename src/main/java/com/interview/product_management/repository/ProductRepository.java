package com.interview.product_management.repository;

import com.interview.product_management.dto.product.ProductDto;
import com.interview.product_management.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(
            value = "SELECT p.id AS id, " +
                    "p.name AS name, " +
                    "p.price AS price," +
                    "p.quantity AS quantity FROM products AS p " +
                    "WHERE p.product_status = 'ENABLE'",
            nativeQuery = true
    )
    List<ProductDto> findAllActiveProducts();
}
