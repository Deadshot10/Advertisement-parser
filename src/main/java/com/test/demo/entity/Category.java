package com.test.demo.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Category {

    @Id
    @GeneratedValue
    public Long id;

    @Column(unique = true)
    public String name;

    @OneToMany
    public Set<Item> items;
}
