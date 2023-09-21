package com.swp391.DogCatLoverPlatform.entity;

import javax.persistence.*;
import java.util.List;

@Entity(name= "blog_type")
public class BlogTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "blogTypeEntity")
    List<BlogEntity> blogEntity;
}
