package com.test.demo.repositories;

import com.test.demo.entity.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Category findOneCategoryByName(String name);
}
