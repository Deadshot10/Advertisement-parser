package com.test.demo.controller;

import com.test.demo.entity.Item;
import com.test.demo.repositories.ItemRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final ItemRepository itemRepository;

    public ApiController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("/visits")
    public Iterable<Item> getVisits() {
        return itemRepository.findAll();
    }


}
