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

    @OneToMany(mappedBy = "blogTypeEntity") //Checked
    List<BlogEntity> blogEntity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BlogEntity> getBlogEntity() {
        return blogEntity;
    }

    public void setBlogEntity(List<BlogEntity> blogEntity) {
        this.blogEntity = blogEntity;
    }
}
