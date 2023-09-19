package com.swp391.CatDogLoverPlatform.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name= "blog")
public class BlogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "tittle")
    private String tittle;

    @Column(name = "content")
    private String content;

    @Column(name = "image")
    private String image;

    @Column(name = "min_price")
    private double min_price;

    @Column(name = "max_price")
    private double max_price;

    @Column(name = "status")
    private boolean status;

    @Column(name = "create_date")
    private Date create_date;

    @Column(name = "confirm")
    private boolean confirm;

    @ManyToOne
    @JoinColumn(name="id_user_create")
    private UserEntity userEntity;

    @Column(name = "id_blog_type")
    private int id_blog_type;

    @ManyToOne
    @JoinColumn(name="id_blog_type",insertable = false, updatable = false)
    private BlogTypeEntity blogTypeEntity;

    @OneToMany(mappedBy = "blogEntity2")
    List<UserBlogCommentEntity>userBlogCommentEntities;

    @OneToMany(mappedBy = "blogEntity4")
    List<BookingEntity>bookingEntities;
}
