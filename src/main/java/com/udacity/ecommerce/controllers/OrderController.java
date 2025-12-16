package com.udacity.ecommerce.controllers;

import com.udacity.ecommerce.model.persistence.User;
import com.udacity.ecommerce.model.persistence.UserOrder;
import com.udacity.ecommerce.security.Context;
import com.udacity.ecommerce.service.OrderService;
import com.udacity.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    public static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @PostMapping("/submit")
    public ResponseEntity<UserOrder> submit(@AuthenticationPrincipal Context ctx) {
        User user = ctx.getUser();
        UserOrder order = orderService.submit(user);
        logger.info("order created for user {}", user.getUsername());
        return ResponseEntity.ok(order);
    }

    @GetMapping("/history")
    public ResponseEntity<List<UserOrder>> getOrdersForUser(@AuthenticationPrincipal Context ctx) {
        User user = ctx.getUser();
        List<UserOrder> orders = orderService.getOrdersForUser(user);
        return ResponseEntity.ok(orders);
    }
}
