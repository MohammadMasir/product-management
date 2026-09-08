package com.interview.product_management.service;

import com.interview.product_management.dto.PriceQuantityProjection;
import com.interview.product_management.dto.product.CartDto;
import com.interview.product_management.dto.product.CartItemsDto;
import com.interview.product_management.dto.product.OrderDetailsDto;
import com.interview.product_management.dto.product.OrderDto;
import com.interview.product_management.enums.orders.PaymentMode;
import com.interview.product_management.enums.orders.PaymentStatus;
import com.interview.product_management.model.Cart;
import com.interview.product_management.model.CartItems;
import com.interview.product_management.model.Product;
import com.interview.product_management.repository.CartItemsRepository;
import com.interview.product_management.repository.CartRepository;
import com.interview.product_management.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemsRepository cartItemsRepository;
    private final ProductRepository productRepository;
    private final OrderService orderService;

    @Transactional
    public void addItemToCart(Long cartId, Long productId) {
        CartItems cartItems = new CartItems();
        cartItems.setCart(cartRepository.getReferenceById(cartId));
        cartItems.setProduct(productRepository.getReferenceById(productId));
        cartItemsRepository.save(cartItems);
    }

    public CartDto getCartItems(Long cartId) {
        List<CartItems> cartItems = cartItemsRepository.findAllByCart(cartRepository.getReferenceById(cartId));
        List<CartItemsDto> cartItemsDtoList = cartItems.stream()
                .map(items -> {
                    return new CartItemsDto(
                            items.getId(),
                            items.getCart().getId(),
                            items.getProduct().getId(),
                            items.getQuantity()
                    );
                }).toList();
        List<PriceQuantityProjection> priceQuantityProjections = cartItemsRepository.getPriceQuantityByCart(cartRepository.getReferenceById(cartId));
        BigDecimal totalAmount = new BigDecimal(0);
        for(PriceQuantityProjection priceQuantityProjection : priceQuantityProjections) {
            totalAmount = priceQuantityProjection.getPrice().multiply(BigDecimal.valueOf(priceQuantityProjection.getQuantity()));
        }
        return new CartDto(cartItemsDtoList, totalAmount);
    }

    @Transactional
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartRepository.getReferenceById(cartId);
        Product product = productRepository.getReferenceById(productId);
        CartItems cartItems = cartItemsRepository.findCartItemsByCartAndProduct(cart, product);
        cartItems.setQuantity(cartItems.getQuantity() - 1);
        cartItemsRepository.save(cartItems);
    }

    @Transactional
    public OrderDetailsDto checkout(OrderDto orderDto) {
        orderService.createOrder(orderDto);
        return null;
    }

}
