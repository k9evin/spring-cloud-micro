package com.example.service;

import com.example.domain.Order;

import java.util.List;

public interface OrderService {
    boolean createOrder(Order order);

    Order getOrder(Long id);

    List<Order> searchOrderByUserId(Long userId);
}
