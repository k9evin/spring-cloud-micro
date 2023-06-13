package com.example.order.service.impl;

import com.example.order.domain.Order;
import com.example.order.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private List<Order> orderList;

    @Override
    public boolean createOrder(Order order) {
        return orderList.add(order);
    }

    @Override
    public Order getOrder(Long id) {
        return orderList.stream().filter(orderItem -> orderItem.getId().equals(id)).findFirst().get();
    }

    @Override
    public List<Order> searchOrderByUserId(Long userId) {
        return orderList.stream().filter(orderItem -> orderItem.getUserId().equals(userId)).collect(Collectors.toList());
    }

    @PostConstruct
    private void initData() {
        orderList = new ArrayList<>();
        orderList.add(new Order(1L, 1L, new Date()));
        orderList.add(new Order(2L, 1L, new Date()));
        orderList.add(new Order(3L, 2L, new Date()));
        orderList.add(new Order(4L, 3L, new Date()));
    }
}
