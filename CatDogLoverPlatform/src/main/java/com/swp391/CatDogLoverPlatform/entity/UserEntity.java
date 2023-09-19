package com.swp391.CatDogLoverPlatform.entity;

import javax.persistence.*;
import java.util.List;

@Entity(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "image")
    private String image;

    @ManyToOne
    @JoinColumn(name="id_role")
    private RoleEntity roleEntity;

    @OneToMany(mappedBy = "userEntity")
    List<BlogEntity> listBlog;

    @OneToMany(mappedBy = "userEntity2")
    List<UserBlogCommentEntity>userBlogCommentEntities ;

    @OneToMany(mappedBy = "userEntity3")
    List<CommentEntity>commentEntities ;

    @OneToMany(mappedBy = "userEntity4")
    List<BookingEntity>bookingEntities ;
}
