package com.udacity.ecommerce.model.persistence.repositories;

import com.udacity.ecommerce.model.persistence.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByName(String name);
}