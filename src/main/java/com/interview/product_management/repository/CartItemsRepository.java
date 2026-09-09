package com.interview.product_management.repository;

import com.interview.product_management.dto.PriceQuantityProjection;
import com.interview.product_management.model.Cart;
import com.interview.product_management.model.CartItems;
import com.interview.product_management.model.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Long> {
    List<CartItems> findAllByCart(Cart cart);

    @Query(value = "SELECT p.price,ci.quantity FROM cart_items AS ci" +
            " JOIN products AS p " +
            "ON ci.cart_item=p.id WHERE ci.cart_id = :cart_id ", nativeQuery = true)
    List<PriceQuantityProjection> getPriceQuantityByCartId(@Param("cart_id") Long cartId);

    Optional<CartItems> findCartItemsByCartAndProduct(Cart cart, Product product);

    @EntityGraph(attributePaths = {"cart_id"})
    List<CartItems> findAllByCart_Id(Long cartId);

    void deleteAllByCart(Cart cart);
}
