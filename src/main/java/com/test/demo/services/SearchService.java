package com.test.demo.services;

import com.test.demo.Util;
import com.test.demo.entity.Category;
import com.test.demo.entity.Item;
import com.test.demo.entity.Region;
import com.test.demo.repositories.CategoryRepository;
import com.test.demo.repositories.ItemRepository;
import com.test.demo.repositories.RegionRepository;
import org.springframework.stereotype.Service;


@Service
public class SearchService {

    private final ItemRepository itemRepository;
    private final RegionRepository regionRepository;
    private final CategoryRepository categoryRepository;

    SearchService(ItemRepository itemRepository,
                          RegionRepository regionRepository,
                          CategoryRepository categoryRepository){
        this.itemRepository = itemRepository;
        this.regionRepository = regionRepository;
        this.categoryRepository = categoryRepository;
    }

    public Iterable<Item> search(String p_keywords, String p_category , String p_region, String p_min, String p_max){
        String keywords = p_keywords.trim();
        Category category = categoryRepository.findOne(Long.parseLong(p_category));
        Region region = regionRepository.findOne(Long.parseLong(p_region));
        long max = Util.tryParse(p_max);
        long min = Util.tryParse(p_min);
        if (category != null && region != null && max != -1L && min != -1L)
            return itemRepository.findAllByDescriptionContainsAndCategoryLikeAndRegionLikeAndPriceBetween(
                    keywords, category, region, max, min);
        else if (category != null && region != null)
            return itemRepository.findAllByDescriptionContainsAndCategoryLikeAndRegionLike(
                    keywords, category, region);
        else if (min != -1L && max != -1L)
            return itemRepository.findAllByDescriptionContainsAndPriceBetween(keywords, min, max);
        else if (min != -1L)
            return itemRepository.findAllByDescriptionContainsAndPriceIsGreaterThanEqual(keywords, min);
        else if (max != -1L)
            return itemRepository.findAllByDescriptionContainsAndPriceIsLessThanEqual(keywords, max);
        else if (category != null)
                return itemRepository.findAllByDescriptionContainsAndCategoryLike(keywords, category);
        else if (region != null)
                return itemRepository.findAllByDescriptionContainsAndRegionLike(keywords, region);
        return itemRepository.findAllByDescriptionContains(keywords);
    }

    public Iterable<Item> search(String keywords, String category , String region){
        return itemRepository.findAll();
    }

    public Iterable<Item> search(String keyword){
        return itemRepository.findAllByDescriptionContains(keyword);
    }

}

//class ItemSpecification {
//    public static Specification<Item> itemContainsWord(final String word) {
//        return new Specification<Item>() {
//            @Override
//            public Predicate toPredicate(Root<Item> root,
//                                         CriteriaQuery<?> criteriaQuery,
//                                         CriteriaBuilder criteriaBuilder) {
//                return criteriaBuilder.equal(root.get("description"), word);
//            }
//        };
//    }
//}