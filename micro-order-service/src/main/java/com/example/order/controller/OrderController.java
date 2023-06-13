package com.example.order.controller;

import com.example.BaseResponse;
import com.example.ResultUtils;
import com.example.order.domain.Order;
import com.example.order.service.OrderService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Order controller.
 */
@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderService orderService;

//    @Resource
//    private UserService userService;
//
//    @GetMapping("/hello")
//    public void hello() {
//        System.out.println(userService.testHello());
//    }

    @PostMapping("/create")
    @ApiOperation("创建新订单")
    public BaseResponse<Boolean> createOrder(@RequestBody Order order) {
        Boolean flag = orderService.createOrder(order);
        log.info("创建订单，下单用户为：{}", order.getUserId());
        return ResultUtils.success(flag);
    }

    @GetMapping("/search/id")
    @ApiOperation("根据id获取订单信息")
    public BaseResponse<Order> getOrder(@RequestParam Long id) {
        Order order = orderService.getOrder(id);
        log.info("根据id获取订单信息，下单用户为：{}", order.getUserId());
        return ResultUtils.success(order);
    }

    @GetMapping("/search/userId")
    @ApiOperation("根据userId获取订单信息")
    public BaseResponse<List<Order>> getOrderByUserId(@RequestParam Long userId) {
        List<Order> orderList = orderService.searchOrderByUserId(userId);
        log.info("根据userId获取订单信息，订单列表为：{}", orderList);
        return ResultUtils.success(orderList);
    }
}
