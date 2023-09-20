package com.example.CatDogLoverPlatform.entity;

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
    @JoinColumn(name="id_role")         //Checked
    private RoleEntity roleEntity;

    @OneToMany(mappedBy = "userEntity")    //Checked
    List<BlogEntity> listBlogs;

    @OneToMany(mappedBy = "userEntity_UserBlogCommentEntity")
    List<UserBlogCommentEntity>userBlogCommentEntities_UserEntity ;

    @OneToMany(mappedBy = "userEntity_CommentEntity")
    List<CommentEntity>commentEntities_UserEntity ;

    @OneToMany(mappedBy = "userEntity_BookingEntity")
    List<BookingEntity>bookingEntities_UserEntity ;
}
