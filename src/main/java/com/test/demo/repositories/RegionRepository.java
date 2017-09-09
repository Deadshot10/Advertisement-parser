package com.test.demo.repositories;

import com.test.demo.entity.Region;
import org.springframework.data.repository.CrudRepository;

public interface RegionRepository extends CrudRepository<Region, Long> {

    Region findOneRegionByName(String name);
}
