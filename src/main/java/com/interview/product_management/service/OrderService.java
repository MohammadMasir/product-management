package com.interview.product_management.service;

import com.interview.product_management.dto.product.CartItemsDto;
import com.interview.product_management.dto.product.OrderDetailsDto;
import com.interview.product_management.dto.product.OrderDto;
import com.interview.product_management.exceptions.ResourceNotFoundException;
import com.interview.product_management.model.Order;
import com.interview.product_management.model.OrderItems;
import com.interview.product_management.repository.OrderItemsRepository;
import com.interview.product_management.repository.OrderRepository;
import com.interview.product_management.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final OrderItemsRepository orderItemsRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderDetailsDto createOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setItemCount(orderDto.cartDto().cartItems().size());
        order.setPaymentMode(orderDto.paymentMode());
        order.setPaymentStatus(orderDto.paymentStatus());
        order.setDeliveryStatus(orderDto.deliveryStatus());
        List<CartItemsDto> cartItems = orderDto.cartDto().cartItems();
        int totalQuantity = 0;
        for (CartItemsDto cartItem : cartItems) {
            totalQuantity = totalQuantity + cartItem.quantity();
        }
        order.setTotalQuantity(totalQuantity);
        order.setTotalAmount(orderDto.cartDto().totalAmount());
        Order order1 = orderRepository.save(order);
        addOrderItems(order1.getId(), cartItems);
        return new OrderDetailsDto(
                order1.getId(),
                order1.getItemCount(),
                orderDto.cartDto().totalAmount(),
                orderDto.paymentMode(),
                orderDto.deliveryStatus(),
                orderDto.paymentStatus()
        );
    }

    public void addOrderItems(Long orderId, List<CartItemsDto> cartItems){
        List<Long> productIdList = cartItems.stream().map(CartItemsDto::productId).toList();
        for (Long productId : productIdList) {
            OrderItems orderItems = new OrderItems();
            orderItems.setOrders(orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order NOT Found!")));
            orderItems.setProduct(productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product NOT Found!")));
            orderItemsRepository.save(orderItems);
        }
        for (CartItemsDto cartItem : cartItems) {
            productService.buyProduct(cartItem.productId(), cartItem.quantity());
        }
    }
}
