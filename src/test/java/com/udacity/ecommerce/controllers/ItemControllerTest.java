package com.udacity.ecommerce.controllers;

import com.udacity.ecommerce.model.persistence.Item;
import com.udacity.ecommerce.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {

    @InjectMocks
    private ItemController itemController;

    @Mock
    private ItemService itemService;

    private Item item;

    @BeforeEach
    public void setUp() {
        item = new Item();
        item.setId(1L);
        item.setName("Test Item");
        item.setPrice(BigDecimal.TEN);
    }

    @Test
    public void getItems_happy_path() {
        Page<Item> itemPage = new PageImpl<>(Collections.singletonList(item));
        when(itemService.findAll(PageRequest.of(0, 1))).thenReturn(itemPage);

        ResponseEntity<PageImpl<Item>> response = itemController.getItems(PageRequest.of(0, 1));

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getContent().size());
        assertEquals(item.getName(), response.getBody().getContent().get(0).getName());
    }

    @Test
    public void getItemById_happy_path() {
        when(itemService.get(item.getId())).thenReturn(item);

        ResponseEntity<Item> response = itemController.getItemById(item.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(item.getName(), response.getBody().getName());
    }

    @Test
    public void getItemsByName_happy_path() {
        List<Item> items = Collections.singletonList(item);
        when(itemService.findByName(item.getName())).thenReturn(items);

        ResponseEntity<List<Item>> response = itemController.getItemsByName(item.getName());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(item.getName(), response.getBody().get(0).getName());
    }
}
