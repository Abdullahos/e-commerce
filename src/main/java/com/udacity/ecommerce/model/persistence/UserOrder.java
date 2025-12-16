package com.udacity.ecommerce.model.persistence;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_order")
@Getter
@Setter
public class UserOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    @Column
    private Long id;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonProperty
    private List<Item> items;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    @JsonProperty
    private User user;

    @JsonProperty
    @Column
    private BigDecimal total;

    public static UserOrder createFromCart(Cart cart) {
        UserOrder order = new UserOrder();
        order.setItems(new ArrayList<>(cart.getItems()));
        order.setTotal(cart.getTotal());
        order.setUser(cart.getUser());
        return order;
    }

}
