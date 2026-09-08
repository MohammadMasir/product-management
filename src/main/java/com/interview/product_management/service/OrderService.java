package com.interview.product_management.service;

import com.interview.product_management.dto.product.OrderDto;
import com.interview.product_management.model.Order;
import com.interview.product_management.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    public void createOrder(OrderDto orderDto) {
        Order order = new Order();
        // order.setItemCount();
        order.setPaymentMode(orderDto.paymentMode());
        order.setPaymentStatus(orderDto.paymentStatus());
       // order.setTotalQuantity(orderDto.);
//        order.se
    }
}
