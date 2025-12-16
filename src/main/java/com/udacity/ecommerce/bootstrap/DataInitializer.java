package com.udacity.ecommerce.bootstrap;

import com.udacity.ecommerce.model.persistence.Item;
import com.udacity.ecommerce.model.persistence.repositories.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    private final ItemRepository itemRepository;

    public DataInitializer(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public void run(String... args) {
        log.info("Initializing item data...");

        Item item1 = new Item();
        item1.setName("Round Widget");
        item1.setPrice(new BigDecimal("2.99"));
        item1.setDescription("A widget that is round");
        itemRepository.save(item1);

        log.info("... finished data initialization.");
    }
}