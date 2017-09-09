package com.test.demo.entity;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;

@Entity
public class Item {

    @Id
    @GeneratedValue
    public Long id;

    @Column(length = 20000)
    public String description;

    public String price;

    @Column(unique = true)
    public String uri;

    @Column(length = 20000)
    public String options_map_json;

    @ManyToOne
    //@JoinColumn(name = "region")
    public Region region;

    @ManyToOne
    //@JoinColumn(name = "category")
    public Category category;

}
