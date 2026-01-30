package com.lloyds.onlineshoppingsystem.controller;

import com.lloyds.onlineshoppingsystem.model.Order;
import com.lloyds.onlineshoppingsystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/placeorder/{userId}")
    public String placeOrder(@PathVariable Long userId) {
        orderService.placeOrder(userId);
        return "Order placed successfully";
    }

    @GetMapping("/history/{userId}")
    public List<Order> orderHistory(@PathVariable Long userId) {
        return orderService.getOrdersByUser(userId);
    }
}
