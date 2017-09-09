package com.test.demo.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Region {

    @Id
    @GeneratedValue
    public Long id;

    @Column(unique = true)
    public String name;

    @OneToMany
    public Set<Item> items;

    @Deprecated
    public Region getRegionByName(String name){
        return new Region();
    }
}
