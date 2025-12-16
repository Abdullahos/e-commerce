package com.udacity.ecommerce.service;

import com.udacity.ecommerce.model.persistence.Item;
import com.udacity.ecommerce.model.persistence.repositories.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Item get(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));
        return item;
    }

    public List<Item> findByName(String name) {
        List<Item> items = itemRepository.findByName(name);
        return items;
    }

    public Page<Item> findAll(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }
}
