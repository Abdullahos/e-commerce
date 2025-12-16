package com.udacity.ecommerce.model.persistence;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

@Entity
@Table(name = "cart")
@Getter
@Setter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;

    @ManyToMany
    @JsonProperty
    private List<Item> items = new ArrayList<>();

    @OneToOne(mappedBy = "cart")
    private User user;

    @Column
    @JsonProperty
    private BigDecimal total = BigDecimal.ZERO;

    public void addItem(Item item) {
        Hibernate.initialize(getItems());
        getItems().add(item);
        total = total.add(item.getPrice());
    }

    public void removeItem(Item item) {
        items.remove(item);
        total = total.subtract(item.getPrice());
    }
}
