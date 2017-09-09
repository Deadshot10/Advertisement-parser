package com.test.demo.repositories;

import com.test.demo.entity.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> {

    Item findOneItemByUri(String uri);
}
