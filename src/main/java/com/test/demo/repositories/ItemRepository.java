package com.test.demo.repositories;

import com.test.demo.entity.Category;
import com.test.demo.entity.Item;
import com.test.demo.entity.Region;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long>{

    Item findOneItemByUri(String uri);

    Iterable<Item> findAllByDescriptionContains(String keyword);

    Iterable<Item> findAllByDescriptionContainsAndCategoryLikeAndRegionLike(String keyword, Category category, Region region);

    Iterable<Item> findAllByDescriptionContainsAndCategoryLikeAndRegionLikeAndPriceBetween(String keyword, Category category, Region region, long min, long max);

    Iterable<Item> findAllByDescriptionContainsAndPriceBetween(String keyword, long min, long max);

    Iterable<Item> findAllByDescriptionContainsAndPriceIsLessThanEqual(String keyword, long max);

    Iterable<Item> findAllByDescriptionContainsAndPriceIsGreaterThanEqual(String keyword, long min);

    Iterable<Item> findAllByDescriptionContainsAndCategoryLike(String keyword, Category category);

    Iterable<Item> findAllByDescriptionContainsAndRegionLike(String keyword, Region region);
}
