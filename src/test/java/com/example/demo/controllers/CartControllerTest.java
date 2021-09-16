package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class CartControllerTest {
    private CartController cartController;
    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void init(){
        cartController = new CartController();
        TestUtils.injectObjects(cartController,"userRepository",userRepository);
        TestUtils.injectObjects(cartController,"cartRepository",cartRepository);
        TestUtils.injectObjects(cartController,"itemRepository",itemRepository);
    }
    @Test
    public void add_To_cart_happy_path(){
        String fakeUserUsername = "name test";
        User fakeUser = new User();
        fakeUser.setUsername("fakeUserUsername");

        Item item = new Item();
        item.setName("item1 name test");
        item.setPrice(BigDecimal.valueOf(1000));

        Cart cart = new Cart();
        fakeUser.setCart(cart);

        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername(fakeUserUsername);
        request.setItemId(0);
        request.setQuantity(2);

        when(userRepository.findByUsername(request.getUsername())).thenReturn(fakeUser);
        when(itemRepository.findById(request.getItemId())).thenReturn(Optional.of(item));

        final ResponseEntity<Cart> cartResponseEntity = cartController.addTocart(request);
        Assertions.assertNotNull(cartResponseEntity);
        Assertions.assertEquals(200,cartResponseEntity.getStatusCodeValue());
        final Cart retrievedCart = cartResponseEntity.getBody();
        Assertions.assertNotNull(retrievedCart);
        Assertions.assertTrue(retrievedCart.getItems().size()==2);
    }
    @Test
    public void remove_From_cart_happy_path(){
        String fakeUserUsername = "name test";
        User fakeUser = new User();
        fakeUser.setUsername("fakeUserUsername");

        Item item = new Item();
        item.setName("item1 name test");
        item.setId(0L);
        item.setPrice(BigDecimal.valueOf(1000));

        Cart cart = new Cart();
        cart.addItem(item);
        fakeUser.setCart(cart);

        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername(fakeUserUsername);
        request.setQuantity(1);

        when(userRepository.findByUsername(request.getUsername())).thenReturn(fakeUser);
        when(itemRepository.findById(request.getItemId())).thenReturn(Optional.of(item));

        final ResponseEntity<Cart> cartResponseEntity = cartController.removeFromcart(request);
        Assertions.assertNotNull(cartResponseEntity);
        final Cart retrievedCart = cartResponseEntity.getBody();
        Assertions.assertTrue(retrievedCart.getItems().size()==0);
    }
    @Test
    public void add_to_cart_with_invalid_username(){
        String invalid_Username = "invalid";

        String fakeUserUsername = "name test";
        User fakeUser = new User();
        fakeUser.setUsername("fakeUserUsername");

        Item item = new Item();
        item.setName("item1 name test");
        item.setPrice(BigDecimal.valueOf(1000));

        Cart cart = new Cart();
        fakeUser.setCart(cart);

        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername(invalid_Username);
        request.setItemId(0);
        request.setQuantity(2);

        when(itemRepository.findById(request.getItemId())).thenReturn(Optional.of(item));

        final ResponseEntity<Cart> cartResponseEntity = cartController.addTocart(request);
        Assertions.assertEquals(404,cartResponseEntity.getStatusCodeValue());
    }
    @Test
    public void add_to_cart_with_invalid_item_id(){
        User fakeUser = new User();
        fakeUser.setUsername("fakeUserUsername");

        ModifyCartRequest request = new ModifyCartRequest();
        request.setQuantity(1);
        request.setItemId(1);
        request.setUsername("fakeUserUsername");

        when(userRepository.findByUsername(request.getUsername())).thenReturn(fakeUser);

        final ResponseEntity<Cart> cartResponseEntity = cartController.addTocart(request);
        Assertions.assertEquals(404,cartResponseEntity.getStatusCodeValue());

    }
    @Test
    public void remove_from_cart_with_invalid_username(){
        String invalid_Username = "invalid";

        String fakeUserUsername = "name test";
        User fakeUser = new User();
        fakeUser.setUsername("fakeUserUsername");

        Item item = new Item();
        item.setName("item1 name test");
        item.setPrice(BigDecimal.valueOf(1000));

        Cart cart = new Cart();
        fakeUser.setCart(cart);

        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername(invalid_Username);
        request.setItemId(0);
        request.setQuantity(2);

        when(itemRepository.findById(request.getItemId())).thenReturn(Optional.of(item));

        final ResponseEntity<Cart> cartResponseEntity = cartController.removeFromcart(request);
        Assertions.assertEquals(404,cartResponseEntity.getStatusCodeValue());
    }
    @Test
    public void remove__cart_with_invalid_item_id(){
        User fakeUser = new User();
        fakeUser.setUsername("fakeUserUsername");

        ModifyCartRequest request = new ModifyCartRequest();
        request.setQuantity(1);
        request.setItemId(1);
        request.setUsername("fakeUserUsername");

        when(userRepository.findByUsername(request.getUsername())).thenReturn(fakeUser);

        final ResponseEntity<Cart> cartResponseEntity = cartController.removeFromcart(request);
        Assertions.assertEquals(404,cartResponseEntity.getStatusCodeValue());

    }
}
