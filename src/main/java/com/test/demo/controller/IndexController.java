package com.test.demo.controller;

import com.test.demo.repositories.CategoryRepository;
import com.test.demo.repositories.ItemRepository;
import com.test.demo.repositories.RegionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
public class IndexController {


    private final RegionRepository regionRepository;
    private final CategoryRepository categoryRepository;

    public IndexController(RegionRepository regionRepository,
                           CategoryRepository categoryRepository) {
        this.regionRepository = regionRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/")
    public ModelAndView index() throws Exception {
        Map<String, Set<Map.Entry<Long, String>>> model = new HashMap<>();

        Map<Long, String> regions = new HashMap<>();
        regionRepository.findAll().forEach((region -> regions.put(region.id, region.name)));
        Set<Map.Entry<Long, String>> regionsSet = regions.entrySet();

        Map<Long, String> categories = new HashMap<>();
        categoryRepository.findAll().forEach(category -> categories.put(category.id, category.name));
        Set<Map.Entry<Long, String>> categoriesSet = categories.entrySet();

        model.put("regions", regionsSet);
        model.put("categories", categoriesSet);

        return new ModelAndView("index", model);
    }



}
