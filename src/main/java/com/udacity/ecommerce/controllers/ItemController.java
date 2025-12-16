package com.udacity.ecommerce.controllers;

import java.util.List;

import com.udacity.ecommerce.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udacity.ecommerce.model.persistence.Item;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {

	private final ItemService itemService;

	@GetMapping
	public ResponseEntity<PageImpl<Item>> getItems(@PageableDefault Pageable pageable) {
		Page<Item> items = itemService.findAll(pageable);

		PageImpl<Item> pageImpl = new PageImpl<>(
				items.getContent(),
				pageable,
				items.getTotalElements()
		);

		return ResponseEntity.ok(pageImpl);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Item> getItemById(@PathVariable Long id) {
		Item item = itemService.get(id);
		return ResponseEntity.ok(item);
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Item>> getItemsByName(@PathVariable String name) {
		List<Item> items = itemService.findByName(name);
		return ResponseEntity.ok(items);
	}
	
}
