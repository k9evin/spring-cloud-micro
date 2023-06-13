package com.example.order.service;

import com.example.order.domain.Order;

import java.util.List;

public interface OrderService {
    boolean createOrder(Order order);

    Order getOrder(Long id);

    List<Order> searchOrderByUserId(Long userId);
}
