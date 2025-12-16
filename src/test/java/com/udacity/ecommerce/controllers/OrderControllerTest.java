package com.udacity.ecommerce.controllers;

import com.udacity.ecommerce.model.persistence.Cart;
import com.udacity.ecommerce.model.persistence.User;
import com.udacity.ecommerce.model.persistence.UserOrder;
import com.udacity.ecommerce.security.Context;
import com.udacity.ecommerce.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    private User user;
    private Context context;
    private UserOrder order;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setCart(new Cart());

        context = new Context();
        context.setUser(user);

        order = new UserOrder();
        order.setId(1L);
        order.setUser(user);
    }

    @Test
    public void submit_order_happy_path() {
        when(orderService.submit(user)).thenReturn(order);

        ResponseEntity<UserOrder> response = orderController.submit(context);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(order.getId(), response.getBody().getId());
    }

    @Test
    public void getOrdersForUser_happy_path() {
        List<UserOrder> orders = Collections.singletonList(order);
        when(orderService.getOrdersForUser(user)).thenReturn(orders);

        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser(context);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(order.getId(), response.getBody().get(0).getId());
    }
}
