package com.interview.product_management.service;

import com.interview.product_management.dto.PriceQuantityProjection;
import com.interview.product_management.dto.product.CartDto;
import com.interview.product_management.dto.product.CartItemsDto;
import com.interview.product_management.dto.product.OrderDetailsDto;
import com.interview.product_management.dto.product.OrderDto;
import com.interview.product_management.model.Cart;
import com.interview.product_management.model.CartItems;
import com.interview.product_management.model.Product;
import com.interview.product_management.model.User;
import com.interview.product_management.repository.CartItemsRepository;
import com.interview.product_management.repository.CartRepository;
import com.interview.product_management.repository.ProductRepository;
import com.interview.product_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
    private final UserRepository userRepository;

    @Transactional
    public void addItemToCart(CartItemsDto cartItem, User user) {
        CartItems cartItems = new CartItems();
        Cart cart = cartRepository.findByUsers_Id(user.getId());
        cartItems.setCart(cart);
        cartItems.setProduct(productRepository.getReferenceById(cartItem.productId()));
        cartItemsRepository.save(cartItems);
    }

    public CartDto getCartItemsById(User user) {
        Cart cart = cartRepository.findByUsers_Id(user.getId());
        List<CartItems> cartItems = cartItemsRepository.findAllByCart_Id(cart.getId());
        List<CartItemsDto> cartItemsDtoList = cartItems.stream()
                .map(items -> new CartItemsDto(
                        items.getId(),
                        items.getProduct().getId(),
                        items.getQuantity()
                )).toList();
        List<PriceQuantityProjection> priceQuantityProjections = cartItemsRepository.getPriceQuantityByCartId(cart.getId());
        BigDecimal totalAmount = new BigDecimal(0);
        for (PriceQuantityProjection priceQuantityProjection : priceQuantityProjections) {
            totalAmount = totalAmount.add(priceQuantityProjection.getPrice()
                    .multiply(
                            BigDecimal.valueOf(priceQuantityProjection.getQuantity())
                    )
            );
        }
        return new CartDto(cartItemsDtoList, totalAmount);
    }

    @Transactional
    public void updateCartQuantity(CartItemsDto cartItemsDto, User user) {
        Cart cart = cartRepository.findByUsers_Id(user.getId());
        Product product = productRepository.getReferenceById(cartItemsDto.productId());
        CartItems cartItems = cartItemsRepository.findCartItemsByCartAndProduct(cart, product).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "No such Product or Cart present!"
                )
        );
        if (cartItemsDto.quantity() == 0) {
            cartItemsRepository.deleteById(cartItemsDto.id());
            return;
        }
        cartItems.setQuantity(cartItemsDto.quantity());
        cartItemsRepository.save(cartItems);
    }

    @Transactional
    public OrderDetailsDto checkout(OrderDto orderDto, User user) {
        User user1 = userRepository.getReferenceById(user.getId());
        Cart cart = cartRepository.findByUsers_Id(user.getId());

        List<PriceQuantityProjection> priceQuantityProjections = cartItemsRepository.getPriceQuantityByCartId(cart.getId());

        if (priceQuantityProjections.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Cart is empty!"
            );
        }

        BigDecimal totalAmount = new BigDecimal(0);
        for (PriceQuantityProjection priceQuantityProjection : priceQuantityProjections) {
            totalAmount = totalAmount.add(priceQuantityProjection.getPrice()
                    .multiply(
                            BigDecimal.valueOf(priceQuantityProjection.getQuantity())
                    )
            );
        }
        OrderDetailsDto orderDetails = orderService.createOrder(orderDto, user1, cart.getId(), totalAmount);
        clearCart(user1);
        return orderDetails;
    }

    @Transactional
    public void clearCart(User user) {
        Cart cart = cartRepository.findByUsers_Id(user.getId());
        cartItemsRepository.deleteAllByCart(cart);
    }

}
