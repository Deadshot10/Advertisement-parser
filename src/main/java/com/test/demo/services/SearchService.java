package com.test.demo.services;

import com.test.demo.Advertisement;
import com.test.demo.entity.Item;
import com.test.demo.repositories.CategoryRepository;
import com.test.demo.repositories.ItemRepository;
import com.test.demo.repositories.RegionRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

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

    //get ads where DESCRIPTION, OPTIONS contains keywords and
    //              CATEGORY = category and
    //              REGION = region
    public Iterable<Item> search(String keywords, long category , long region){
        return itemRepository.findAll();
    }

    //EntityManager entityManager;

//    public Iterable<Item> findAllByKeywords(List<String> keywords){
//        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Item> query = builder.createQuery(Item.class);
//        Root<Item> root = query.from(Item.class);
//
//        List<Predicate> predicates = new LinkedList<>();
//        for (String keyword : keywords) {
//            predicates.add(builder.like(root.<String>get("keywords"), "%" + keyword + "%"));
//        }
//
//        return entityManager.createQuery(
//                query.select(root).where(
//                        builder.or(
//                                predicates.toArray(new Predicate[predicates.size()])
//                        )
//                ))
//                .getResultList();
//    }
}
