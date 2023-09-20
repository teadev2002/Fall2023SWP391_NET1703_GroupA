package com.swp391.CatDogLoverPlatform.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name= "blog")
public class BlogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "image")
    private String image;

    @Column(name = "min_price")
    private double minPrice;

    @Column(name = "max_price")
    private double maxPrice;

    @Column(name = "status")
    private boolean status;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "confirm")
    private boolean confirm;

    @ManyToOne
    @JoinColumn(name="id_user_created")     //Checked
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name="id_blog_type",insertable = false, updatable = false)
    private BlogTypeEntity blogTypeEntity;  

    @OneToMany(mappedBy = "blogEntity_UserBlogCommentEntity")
    List<UserBlogCommentEntity>userBlogCommentEntities_BlogEntity;

    @OneToMany(mappedBy = "blogEntity_BookingEntity")
    List<BookingEntity>bookingEntities_BlogEntity;
}
