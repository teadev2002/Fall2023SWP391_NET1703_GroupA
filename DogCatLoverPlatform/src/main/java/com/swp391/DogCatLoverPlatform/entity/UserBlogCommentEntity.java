package com.swp391.DogCatLoverPlatform.entity;


import com.swp391.DogCatLoverPlatform.entity.keys.UserBlogCommentKey;

import javax.persistence.*;

@Entity(name = "user_blog_comment")
public class UserBlogCommentEntity {

    @EmbeddedId
    private UserBlogCommentKey userBlogCommentKey;

    @ManyToOne
    @JoinColumn(name="id_user",insertable = false, updatable = false) // tên cột khóa ngoại trong database  //Checked
    private UserEntity userEntity_UserBlogCommentEntity; // tên Entity tham chiếu tới

    @ManyToOne
    @JoinColumn(name="id_blog",insertable = false, updatable = false) // tên cột khóa ngoại trong database  //Checked
    private BlogEntity blogEntity_UserBlogCommentEntity; // tên Entity tham chiếu tới

    @OneToOne
    @JoinColumn(name="id_comment",insertable = false, updatable = false) // tên cột khóa ngoại trong database
    private CommentEntity commentEntity_UserBlogCommentEntity; // tên Entity tham chiếu tới



}
