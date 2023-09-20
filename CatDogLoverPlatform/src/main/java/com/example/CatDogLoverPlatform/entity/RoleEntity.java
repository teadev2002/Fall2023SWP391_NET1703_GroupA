package com.example.CatDogLoverPlatform.entity;

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

    @OneToMany(mappedBy = "roleEntity")     //Checked
    private List<UserEntity> listUsers;

}
