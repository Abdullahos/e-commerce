package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {
    private ItemController itemController;
    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void init(){
        itemController = new ItemController();
        TestUtils.injectObjects(itemController,"itemRepository",itemRepository);
    }
    @Test
    public void get_All_items(){
        String itemName = "name test";
        Item item1 = new Item();
        item1.setName(itemName);
        item1.setPrice(BigDecimal.valueOf(1000));

        Item item2 = new Item();
        item2.setName(itemName);
        item2.setPrice(BigDecimal.valueOf(10));
        when(itemRepository.findAll()).thenReturn(Arrays.asList(item1,item2));

        final ResponseEntity<List<Item>> items = itemController.getItems();
        Assertions.assertNotNull(items);
        Assertions.assertEquals(200,items.getStatusCodeValue());
        Assertions.assertTrue(items.getBody().containsAll(Arrays.asList(item1,item2)) && items.getBody().size()==2);
    }
    @Test
    public void getItemById(){
        Item item = new Item();
        item.setName("name test");
        item.setPrice(BigDecimal.valueOf(1000));
        when(itemRepository.findById(1L)).thenReturn(java.util.Optional.of(item));
        final ResponseEntity<Item> itemById = itemController.getItemById(1L);
        Assertions.assertNotNull(itemById);
        Assertions.assertEquals(200,itemById.getStatusCodeValue());
    }
    @Test
    public void get_list_of_items_by_name(){
        String itemName = "name test";
        Item item1 = new Item();
        item1.setName(itemName);
        item1.setPrice(BigDecimal.valueOf(1000));

        Item item2 = new Item();
        item2.setName(itemName);
        item2.setPrice(BigDecimal.valueOf(10));
        when(itemRepository.findByName(itemName)).thenReturn(Arrays.asList(item1,item2));
        final ResponseEntity<List<Item>> itemsByName = itemController.getItemsByName(itemName);
        Assertions.assertNotNull(itemsByName);
        Assertions.assertEquals(200,itemsByName.getStatusCodeValue());
        Assertions.assertEquals(Arrays.asList(item1,item2),Arrays.asList(item1,item2));
    }
}
