package com.udacity.ecommerce.service;

import com.udacity.ecommerce.model.persistence.User;
import com.udacity.ecommerce.model.persistence.UserOrder;
import com.udacity.ecommerce.model.persistence.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;


    public UserOrder submit(User user) {
        User persistedUser = userService.findByUserName(user.getUsername());
        UserOrder order = UserOrder.createFromCart(persistedUser.getCart());
        return orderRepository.save(order);
    }

    public List<UserOrder> getOrdersForUser(User user) {
        return orderRepository.findByUser(user);
    }
}
