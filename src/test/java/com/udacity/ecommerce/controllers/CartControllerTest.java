package com.udacity.ecommerce.controllers;

import com.udacity.ecommerce.model.persistence.Cart;
import com.udacity.ecommerce.model.persistence.Item;
import com.udacity.ecommerce.model.persistence.User;
import com.udacity.ecommerce.model.requests.ModifyCartRequest;
import com.udacity.ecommerce.security.Context;
import com.udacity.ecommerce.service.CartService;
import com.udacity.ecommerce.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartControllerTest {

    @InjectMocks
    private CartController cartController;

    @Mock
    private CartService cartService;

    @Mock
    private ItemService itemService;

    private User user;
    private Context context;
    private Item item;
    private Cart cart;
    private ModifyCartRequest request;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        context = new Context();
        context.setUser(user);

        item = new Item();
        item.setId(1L);
        item.setName("Test Item");
        item.setPrice(BigDecimal.TEN);

        cart = new Cart();
        cart.setId(1L);
        cart.setUser(user);
        user.setCart(cart);

        request = new ModifyCartRequest();
        request.setItemId(item.getId());
        request.setQuantity(1);
    }

    @Test
    public void addToCart_happy_path() {
        when(itemService.get(request.getItemId())).thenReturn(item);
        when(cartService.addToCart(request, user, item)).then(invocation -> {
            cart.addItem(item);
            return cart;
        });

        ResponseEntity<Cart> response = cartController.addToCart(context, request);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Cart responseCart = response.getBody();
        assertNotNull(responseCart);
        assertEquals(1, responseCart.getItems().size());
        assertEquals(item.getName(), responseCart.getItems().get(0).getName());
    }

    @Test
    public void removeFromCart_happy_path() {
        // Add an item to the cart first to be able to remove it
        cart.addItem(item);

        when(itemService.get(request.getItemId())).thenReturn(item);
        when(cartService.removeFromCart(request, user, item)).then(invocation -> {
            cart.removeItem(item);
            return cart;
        });

        ResponseEntity<Cart> response = cartController.removeFromCart(context, request);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Cart responseCart = response.getBody();
        assertNotNull(responseCart);
        assertTrue(responseCart.getItems().isEmpty());
    }
}
