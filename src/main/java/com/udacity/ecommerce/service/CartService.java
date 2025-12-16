package com.udacity.ecommerce.service;

import com.udacity.ecommerce.model.persistence.Cart;
import com.udacity.ecommerce.model.persistence.Item;
import com.udacity.ecommerce.model.persistence.User;
import com.udacity.ecommerce.model.persistence.repositories.CartRepository;
import com.udacity.ecommerce.model.requests.ModifyCartRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

import static com.udacity.ecommerce.controllers.CartController.logger;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserService userService;

    public Cart addToCart(ModifyCartRequest request, User user, Item item) {
        User persistedUser = userService.findByUserName(user.getUsername());
        Cart cart = persistedUser.getCart();
        IntStream.range(0, request.getQuantity())	//todo: check the availability first
                .forEach(i -> cart.addItem(item));

        return cartRepository.save(cart);
    }

    @Transactional
    public Cart removeFromCart(ModifyCartRequest request, User user, Item item) {
        Cart cart = user.getCart();
        IntStream.range(0, request.getQuantity())
                .forEach(i -> cart.removeItem(item));

        logger.info("{} {} removed from cart!", request.getQuantity(), item.getName());
        return cartRepository.save(cart);
    }
}
