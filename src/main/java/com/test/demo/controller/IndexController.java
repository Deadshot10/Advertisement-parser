package com.test.demo.controller;

import com.test.demo.entity.Item;
import com.test.demo.repositories.ItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {

    private final ItemRepository itemRepository;

    public IndexController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("/")
    public ModelAndView index() throws Exception {

        Map<String, String> model = new HashMap<>();

        return new ModelAndView("index", model);
    }



}
