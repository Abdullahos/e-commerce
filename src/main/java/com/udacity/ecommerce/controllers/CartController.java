package com.udacity.ecommerce.controllers;

import java.util.Optional;
import java.util.stream.IntStream;

import com.udacity.ecommerce.security.Context;
import com.udacity.ecommerce.service.CartService;
import com.udacity.ecommerce.service.ItemService;
import com.udacity.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udacity.ecommerce.model.persistence.Cart;
import com.udacity.ecommerce.model.persistence.Item;
import com.udacity.ecommerce.model.persistence.User;
import com.udacity.ecommerce.model.requests.ModifyCartRequest;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    private final ItemService itemService;

    public static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @PostMapping("/addToCart")
    @Transactional
    public ResponseEntity<Cart> addToCart(@AuthenticationPrincipal Context ctx, @RequestBody ModifyCartRequest request) {
        User user = ctx.getUser();

        Item item = itemService.get(request.getItemId());

        Hibernate.initialize(user);

        Cart cart = cartService.addToCart(request, user, item);

        logger.info("{} {} added to cart", request.getQuantity(), item.getName());

        return ResponseEntity.ok(cart);
    }

    @PostMapping("/removeFromCart")
    public ResponseEntity<Cart> removeFromCart(@AuthenticationPrincipal Context ctx, @RequestBody ModifyCartRequest request) {
        User user = ctx.getUser();

        Item item = itemService.get(request.getItemId());

        Cart cart = cartService.removeFromCart(request, user, item);

        return ResponseEntity.ok(cart);
    }

}
