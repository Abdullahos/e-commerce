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

    public UserOrder submit(User user) {
        UserOrder order = UserOrder.createFromCart(user.getCart());
        return orderRepository.save(order);
    }

    public List<UserOrder> getOrdersForUser(User user) {
        return orderRepository.findByUser(user);
    }
}
