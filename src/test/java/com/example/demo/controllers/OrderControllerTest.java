package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * class to test order controller layer
 */
public class OrderControllerTest {
    private OrderController orderController;
    private OrderRepository orderRepository = mock(OrderRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);

    @Before
    public void init(){
        orderController = new OrderController();
        TestUtils.injectObjects(orderController,"orderRepository",orderRepository);
        TestUtils.injectObjects(orderController,"userRepository",userRepository);
    }

    @Test
    public void submit_order_happy_path(){
        String fakeUserUsername = "test name";
        User fakeUser = new User();
        fakeUser.setUsername(fakeUserUsername);
        fakeUser.setPassword("hashedPass");

        UserOrder order = new UserOrder();

        Item item1 = new Item();
        item1.setName("1_item");
        Item item2 = new Item();
        item2.setName("1_item");

        Cart cart = new Cart();
        cart.setUser(fakeUser);
        cart.setItems(Arrays.asList(item1,item2));

        fakeUser.setCart(cart);

        when(userRepository.findByUsername(fakeUserUsername)).thenReturn(fakeUser);

        final ResponseEntity<UserOrder> orderResponseEntity = orderController.submit(fakeUserUsername);
        Assertions.assertNotNull(orderResponseEntity);
        Assertions.assertEquals(200,orderResponseEntity.getStatusCodeValue());
        UserOrder retrieved_submitted_order = orderResponseEntity.getBody();
        Assertions.assertEquals(Arrays.asList(item1,item2), retrieved_submitted_order.getItems());
    }

    @Test
    public void submit_order_for_unavailable_username(){
        String unAvailableUsername = "unAvailableUsername";
        final ResponseEntity<UserOrder> orderResponseEntity = orderController.submit(unAvailableUsername);
        Assertions.assertEquals(404,orderResponseEntity.getStatusCodeValue());

    }
    @Test
    public void get_Orders_by_username_happy_path(){
        String fakeUserUsername = "test name";
        User fakeUser = new User();
        fakeUser.setUsername(fakeUserUsername);
        fakeUser.setPassword("hashedPass");

        UserOrder order = new UserOrder();
        /*not necessary for this test case but i made it for flexibility
        Item item1 = new Item();
        item1.setName("1_item");
        Item item2 = new Item();
        item2.setName("1_item");

        Cart cart = new Cart();
        cart.setUser(fakeUser);
        cart.setItems(Arrays.asList(item1,item2));

        fakeUser.setCart(cart);
        */
        order.setUser(fakeUser);

        when(userRepository.findByUsername(fakeUserUsername)).thenReturn(fakeUser);
        when(orderRepository.findByUser(fakeUser)).thenReturn(Arrays.asList(order));

        final ResponseEntity<List<UserOrder>> ordersForUserResponse = orderController.getOrdersForUser(fakeUserUsername);
        Assertions.assertNotNull(ordersForUserResponse);
        Assertions.assertEquals(200,ordersForUserResponse.getStatusCodeValue());
        final List<UserOrder> ordersForUser = ordersForUserResponse.getBody();
        Assertions.assertTrue(ordersForUser.size()==1);
    }
    @Test
    public void get_order_for_not_found_username(){
        String not_found_username = "notFound";
        final ResponseEntity<List<UserOrder>> ordersForUserResponse = orderController.getOrdersForUser(not_found_username);
        Assertions.assertEquals(404,ordersForUserResponse.getStatusCodeValue());
    }
}
