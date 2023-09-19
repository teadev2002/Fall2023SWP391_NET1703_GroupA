package com.swp391.CatDogLoverPlatform.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name="comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "description")
    private String description;

    @Column(name="create_date")
    private Date createDate;

    @Column(name = "rating")
    private int rating;

    @OneToOne(mappedBy = "commentEntity2")
    UserBlogCommentEntity userBlogCommentEntities;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserEntity userEntity3;

}

