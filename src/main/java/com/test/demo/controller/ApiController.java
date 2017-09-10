package com.test.demo.controller;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.test.demo.Advertisement;
import com.test.demo.Util;
import com.test.demo.entity.Item;
import com.test.demo.repositories.ItemRepository;
import com.test.demo.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    SearchService searchService;

    public ApiController() {
    }

    @GetMapping(value = "/search", params = {"keywords", "region", "category", "min", "max"})
    public ModelAndView search(@RequestParam(value = "keywords") String keywords,
                               @RequestParam(value = "region") String region,
                               @RequestParam(value = "category") String category,
                               @RequestParam(value = "max") String max,
                               @RequestParam(value = "min") String min) throws IOException {
        Map<String, Set<Advertisement>> model = new HashMap<>();

        Iterable<Item> searchResult = searchService.search(keywords, region, category, min, max);

        Set<Advertisement> result = new HashSet<>();
        for (Item item : searchResult) {
            Advertisement advertisement = new Advertisement();
            advertisement.options = (JsonObject) new JsonParser().parse(item.options_map_json);
            advertisement.uri = item.uri;
            advertisement.description = Util.shortString(item.description);
            advertisement.price = item.price;
            result.add(advertisement);
        }
        model.put("result", result);

        return new ModelAndView("search", model);
    }
}
