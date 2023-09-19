package com.swp391.CatDogLoverPlatform.entity;

import javax.persistence.*;
import java.util.List;

@Entity(name = "role")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name ="name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "roleEntity")
    private List<UserEntity> listUsers;

}
